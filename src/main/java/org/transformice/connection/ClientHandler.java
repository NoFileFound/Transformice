package org.transformice.connection;

// Imports
import java.net.InetSocketAddress;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.packets.PacketStruct;

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

        this.server.removeClientSession(context.getChannel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        Application.getLogger().error(Application.getTranslationManager().get("packeterror"), e.getCause());
    }

    @Override
    public void messageReceived(ChannelHandlerContext context, MessageEvent e) {
        if (!(e.getMessage() instanceof ByteArray packet)) {
            return;
        }

        if(this.server.getTempBlackList().contains(((InetSocketAddress)context.getChannel().getRemoteAddress()).getAddress().getHostAddress())) {
            context.getChannel().close();
            return;
        }

        Client client = (Client) context.getChannel().getAttachment();
        if (client == null) {
            this.server.addClientSession(context.getChannel());
            client = (Client) context.getChannel().getAttachment();
        }

        while(packet.getLength() > 0) {
            int packetLength = this.getVLQLength(packet);
            if(packet.getLength() >= packetLength) {
                ByteArray buffer = new ByteArray(packet.readBytes(packetLength));
                int fingerPrint = buffer.readUnsignedByte();
                int C = buffer.readUnsignedByte();
                int CC = buffer.readUnsignedByte();
                boolean isLegacy = false;

                if(C == 1 && CC == 1) {
                    isLegacy = true;
                    buffer.readShort(); // packet length
                    C = buffer.readByte();
                    CC = buffer.readByte();
                    if(buffer.getLength() > 0) buffer.readByte(); // 01
                }

                var handler = client.getServer().getPacketHandler().getHandlers().get(new PacketStruct(((C << 8) | (CC & 0xFF)), isLegacy));
                if (handler != null) {
                    Application.getLogger().debug(Application.getTranslationManager().get("receivedpacket", isLegacy ? "[Legacy]" : "", client.getIpAddress(), C, CC));
                    handler.handle(client, fingerPrint, buffer);
                } else {
                    Application.getLogger().warn(Application.getTranslationManager().get("unhandledpacket", isLegacy ? "[Legacy]" : "", C, CC));
                }
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