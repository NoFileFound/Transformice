package org.transformice.packets.recv.informations;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_GAME_ERROR_LOG)
public final class PacketRecvGameErrorLog extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int C = psData.readByte() & 0xFF;
        int CC = psData.readByte() & 0xFF;
        int OldC = psData.readByte() & 0xFF;
        int OldCC = psData.readByte() & 0xFF;
        String error = psData.readUTF();
        String ip = client.getIpAddress();
        if (C == 1 && CC == 1) {
            gameServer.getLogger().warn(String.format("[PacketHandler] [%s] Error: [%d, %d] (Legacy) | Reason: %s", client.getIpAddress(), OldC, OldCC, error));
        } else if (C == 60) {
            gameServer.getLogger().warn(String.format("[PacketHandler] [%s] Error: (Tribulle) | Reason: %s", client.getIpAddress(), error));
        } else {
            gameServer.getLogger().warn(String.format("[PacketHandler] [%s] Error: [%d, %d] (General) | Reason: %s", client.getIpAddress(), C, CC, error));
        }

        if(!client.getCheckIsDebugException())
            client.disconnect(); // tfm moment.
    }
}