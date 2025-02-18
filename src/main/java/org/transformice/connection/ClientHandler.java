package org.transformice.connection;

// Imports
import java.net.InetSocketAddress;
import java.util.Arrays;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;

public final class ClientHandler extends SimpleChannelHandler {
    private final Server server;

    public ClientHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelClosed(ChannelHandlerContext context, ChannelStateEvent e) {
        Client client = (Client) context.getChannel().getAttachment();
        if (client != null) {
            client.closeConnection();
        }

        this.server.getSessionManager().removeSession(context.getChannel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        Application.getLogger().error("[Packet] Error when receiving packet ", e.getCause());
    }

    @Override
    public void messageReceived(ChannelHandlerContext context, MessageEvent e) {
        if (!(e.getMessage() instanceof byte[] buff) || buff.length < 2) {
            return;
        }

        if(this.server.getTempBlackList().contains(((InetSocketAddress)context.getChannel().getRemoteAddress()).getAddress().getHostAddress())) {
            context.getChannel().close();
            return;
        }

        Client client = (Client) context.getChannel().getAttachment();
        if (client == null) {
            this.server.getSessionManager().addSession(context.getChannel());
            client = (Client) context.getChannel().getAttachment();
        }

        ByteArray packet = new ByteArray(buff);
        int packetLength = this.getVLQLength(packet);
        if (packet.getLength() >= packetLength) {
            packet = new ByteArray(Arrays.copyOfRange(packet.toByteArray(), 0, packetLength));

            int fingerPrint = packet.readUnsignedByte();
            int C = packet.readUnsignedByte();
            int CC = packet.readUnsignedByte();

            var handler = client.getServer().getPacketHandler().getHandlers().get((C << 8) | (CC & 0xFF));
            if (handler != null) {
                Application.getLogger().debug(String.format("[Packet] %s received a packet [%d, %d]", client.getIpAddress(), C, CC));
                handler.handle(client, fingerPrint, packet);
            } else {
                Application.getLogger().warn(String.format("[Packet] Unhandled packet %d -> %d", C, CC));
            }
        }
    }

    /**
     * Calculates the variable length quantity from the processing data.
     * @param data The given data.
     * @return The calculated length.
     */
    private int getVLQLength(ByteArray data) {
        int var2068 = 0;
        int var2053 = 0;
        while (true) {
            int var56 = data.readByte();
            var2068 = var2068 | (var56 & 127) << (7 * var2053);
            var2053++;
            if (!((var56 & 128) == 128 && var2053 < 5)) {
                return var2068 + 1;
            }
        }
    }
}