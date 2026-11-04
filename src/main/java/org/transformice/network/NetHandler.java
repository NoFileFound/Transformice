package org.transformice.network;

// Imports
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.recv.login.PacketRecvLogin;

public final class NetHandler extends ChannelInboundHandlerAdapter
{
    private final GameServer server;

    /**
     * Creates a new network handler.
     * @param server The game server.
     */
    public NetHandler(GameServer server) {
        this.server = server;
    }

    /**
     * Called when a new client connection becomes active.
     * @param context The channel handler context associated with the client connection.
     */
    @Override
    public void channelActive(ChannelHandlerContext context) {
        Channel channel = context.channel();
        String ipAddress = ((InetSocketAddress) context.channel().remoteAddress()).getAddress().getHostAddress();
        Long expiration = this.server.getTempBannedIPs().get(ipAddress);
        if (expiration != null) {
            if (System.currentTimeMillis() < expiration) {
                context.close();
                return;
            }

            this.server.getTempBannedIPs().remove(ipAddress, expiration);
        }

        NetSession session = new NetSession(this.server, context.channel());
        channel.attr(NetSession.CLIENT_KEY).set(session);
        this.server.getClientSessions().put(channel.id(), session);
        session.getKeepAliveTimer().reset();
        this.server.getLogger().debug(String.format("[%s] New session connected -> %s", ipAddress, context.channel().remoteAddress().toString()));
        context.fireChannelActive();
    }

    /**
     * Called when the client connection becomes inactive.
     * @param context The channel handler context associated with the client connection.
     */
    @Override
    public void channelInactive(ChannelHandlerContext context) {
        Channel channel = context.channel();
        String ipAddress = ((InetSocketAddress) context.channel().remoteAddress()).getAddress().getHostAddress();
        NetSession session = channel.attr(NetSession.CLIENT_KEY).get();
        try {
            if (session != null) {
                session.getKeepAliveTimer().cancel();
                var player = session.getPlayer();
                if (player != null)
                    player.disconnect(false);

                NetSession nextPlayer = this.server.getPlayerQueue().leave(session);
                if (nextPlayer != null) {
                    var request = this.server.getPlayerQueue().removeLoginRequest(nextPlayer);
                    if (request != null) {
                        new PacketRecvLogin().handle(this.server, nextPlayer, request.getHeader(), request.getData());
                    }
                }
            }
        } finally {
            this.server.getClientSessions().remove(channel.id());
            this.server.getLogger().debug(String.format("[%s] Client disconnected -> %s", ipAddress, context.channel().remoteAddress().toString()));
            context.fireChannelInactive();
        }
    }

    /**
     * Handles incoming messages from a client connection.
     *
     * @param context The channel handler context associated with the client connection.
     * @param message The incoming message received from the client.
     */
    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        if (!(message instanceof NetByteArray packet)) {
            context.fireChannelRead(message);
            return;
        }

        var session = context.channel().attr(NetSession.CLIENT_KEY).get();
        if (session == null)
            throw new PSReceivePacketException("Received packet without a network session.");

        int fingerPrint = packet.readUnsignedByte();
        int packetOpcode = (packet.readUnsignedByte() << 8) | (packet.readUnsignedByte() & 0xFF);
        var packetInstance = this.server.getPacketHandler().getPacket(packetOpcode);
        if (packetInstance != null) {
            if (packetOpcode != PacketOpcodes.RECV_HANDSHAKE && !session.getCheckHasPassedHandShakePacket()) {
                throw new PSReceivePacketException(String.format("[%s] Tried to send an illegal packet.", session.getIpAddress()));
            }

            if ((packetOpcode == PacketOpcodes.RECV_LOGIN_PLAYER || packetOpcode == PacketOpcodes.RECV_CREATE_ACCOUNT_CAPTCHA_GEN || packetOpcode == PacketOpcodes.RECV_CREATE_ACCOUNT) && !session.getCheckHasPassedBotVerification()) {
                throw new PSReceivePacketException(String.format("[%s] Tried to login/register without passing the antibot verification.", session.getIpAddress()));
            }

            if (session.getCheckHasBanned()) {
                return;
            }

            if ((packetOpcode == PacketOpcodes.RECV_ANTICHEAT_GENERAL || packetOpcode == PacketOpcodes.RECV_ANTICHEAT_INCONSISTENT_TIMING) && !this.server.getConfig().getGameConfig().isEnableServerAnticheat())
                return;

            if (session.getIsDebugPacket())
                this.server.getLogger().debug(String.format("[PacketHandler] [%s] Received packet: %s", session.getIpAddress(), packetOpcode));

            packetInstance.dispatch(this.server, session, new NetByteArray().writeUnsignedByte(fingerPrint), packet);
        } else {
            this.server.getLogger().debug(String.format("[PacketHandler] [%s] Unhandled packet: %s", session.getIpAddress(), packetOpcode));
        }
    }

    /**
     * Handles exceptions raised during client communication.
     * @param context The channel handler context associated with the client connection.
     * @param cause The exception that occurred.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        NetSession session = context.channel().attr(NetSession.CLIENT_KEY).get();
        if (session == null) {
            context.close();
            return;
        }

        this.server.getLogger().error("Client error: ", cause);
        if (!session.getCheckIsDebugException()) {
            context.close();
        }
    }
}