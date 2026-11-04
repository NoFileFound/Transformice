package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.data.objects.ReportType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_REPORT_PLAYER)
public final class PacketRecvReportPlayer extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        String playerName = psData.readUTF();
        int reportByte = psData.readByte();
        String reportComment = psData.readUTF();
        gameServer.getParseModopwet().sendCreateGameReport(client.getPlayer(), playerName, ReportType.fromValue(reportByte), reportComment);
    }
}