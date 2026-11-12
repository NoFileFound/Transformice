package org.transformice.packets.recv.tribe;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_ENTER_TRIBE_HOUSE)
public final class PacketRecvEnterTribeHouse extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        var player = client.getPlayer();
        String tribeName = player.getPlayerData().tribeName;
        if (tribeName.isEmpty()) {
            client.disconnect();
            return;
        }

        player.sendEnterRoom("*" + (char) 3 + tribeName, "");
    }
}