package org.transformice.packets.recv.login;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.data.objects.MonsterInfo;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MONSTER_SYNCHRONIZATION)
public final class PacketRecvMonsterSynchronization extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int monsterCount = psData.readShort();
        List<MonsterInfo> monsterInfoList = new ArrayList<>(monsterCount);
        for (int i = 0; i < monsterCount; i++) {
            int monsterId = psData.readInt();
            int x = psData.readInt();
            int y = psData.readInt();
            monsterInfoList.add(new MonsterInfo(monsterId, x, y));
        }

        gameServer.getParseEvents().handleEventMonsterSynchronization(client.getPlayer().getPlayerRoom(), monsterInfoList);
    }
}