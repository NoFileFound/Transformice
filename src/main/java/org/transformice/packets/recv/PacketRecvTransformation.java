package org.transformice.packets.recv;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.PacketSendTransformation;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_TRANSFORMATION)
public final class PacketRecvTransformation extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        GameRoom room = player.getPlayerRoom();
        if (player.canTransform)
            room.broadcastPacket(new PacketSendTransformation(player.getSessionId(), psData.readShort()));
    }
}