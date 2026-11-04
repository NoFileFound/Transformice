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

// Packets
import org.transformice.packets.send.room.PacketSendSetRoomGravity;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_SET_ROOM_GRAVITY)
public final class PacketRecvSetRoomGravity extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GameRoom room = client.getPlayer().getPlayerRoom();
        if (room.getCurrentMap().isGravity)
            room.broadcastPacket(new PacketSendSetRoomGravity(0, psData.readShort(), psData.readShort()));
    }
}