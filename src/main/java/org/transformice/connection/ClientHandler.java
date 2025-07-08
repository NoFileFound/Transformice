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

        int fingerPrint = packet.readUnsignedByte();
        int C = packet.readUnsignedByte();
        int CC = packet.readUnsignedByte();
        boolean isLegacy = false;
        if(C == 1 && CC == 1) {
            isLegacy = true;
            packet.readShort(); // packet length
            C = packet.readByte();
            CC = packet.readByte();
            if(packet.getLength() > 0) packet.readByte(); // 01
        }

        var handler = client.getServer().getPacketHandler().getHandlers().get(new PacketStruct(((C << 8) | (CC & 0xFF)), isLegacy));
        if (handler != null) {
            Application.getLogger().debug(Application.getTranslationManager().get("receivedpacket", isLegacy ? "[Legacy]" : "", client.getIpAddress(), C, CC));
            handler.handle(client, fingerPrint, packet);
        } else {
            Application.getLogger().warn(Application.getTranslationManager().get("unhandledpacket", isLegacy ? "[Legacy]" : "", C, CC));
        }
    }
}