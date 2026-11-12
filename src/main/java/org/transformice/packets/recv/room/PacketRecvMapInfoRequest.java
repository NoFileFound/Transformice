package org.transformice.packets.recv.room;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.data.objects.CheesePosition;
import org.transformice.data.objects.HolePosition;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MAP_INFO_REQUEST)
public final class PacketRecvMapInfoRequest extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int cheeseLen = psData.readByte() / 2;
        for (int i = 0; i < cheeseLen; i++)
            client.getPlayer().getPlayerRoom().getCheesePositions().add(new CheesePosition(psData.readShort(), psData.readShort()));

        int holeLen = psData.readByte() / 3;
        for (int i = 0; i < holeLen; i++)
            client.getPlayer().getPlayerRoom().getHolePositions().add(new HolePosition(psData.readShort(), psData.readShort(), psData.readShort()));
    }
}