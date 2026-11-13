package org.transformice.packets.recv.room;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MAP_26_PACKET)
public final class PacketRecvMap26Packet extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GameRoom room = client.getPlayer().getPlayerRoom();
        if (room.getCurrentMap().mapCode == 26) {
            int posX = psData.readShort();
            int posY = psData.readShort();
            int width = psData.readShort();
            int height = psData.readShort();
            room.sendAddPhysicObject(-1, false, 0, posX, posY, width, height, false, 0, 0, 0, 0, true, true, false, 0, 0, 0, true, "", false);
        }
    }
}