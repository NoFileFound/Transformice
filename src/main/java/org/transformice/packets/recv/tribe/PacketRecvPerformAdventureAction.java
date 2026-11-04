package org.transformice.packets.recv.tribe;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PERFORM_ADVENTURE_ACTION)
public final class PacketRecvPerformAdventureAction extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int adventureId = psData.readUnsignedByte();
        int actionType = psData.readUnsignedByte();
        int actionSize = psData.readUnsignedByte();
        List<String> actions = new ArrayList<>(actionSize);
        for (int i = 0; i < actionSize; i++)
            actions.add(psData.readUTF());

        gameServer.getParseEvents().handleEventPlayerPerformAction(client.getPlayer().getPlayerRoom(), adventureId, actionType, actions);
    }
}