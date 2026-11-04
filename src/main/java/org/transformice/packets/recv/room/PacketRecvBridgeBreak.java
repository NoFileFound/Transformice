package org.transformice.packets.recv.room;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.room.PacketSendBridgeBreak;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_BRIDGE_BREAK)
public final class PacketRecvBridgeBreak extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        client.getPlayer().getPlayerRoom().broadcastPacket(client.getPlayer(), new PacketSendBridgeBreak(psData.readShort()));
    }
}