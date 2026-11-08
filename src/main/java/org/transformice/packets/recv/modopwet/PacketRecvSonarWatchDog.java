package org.transformice.packets.recv.modopwet;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_SONAR_WATCH_DOG)
public final class PacketRecvSonarWatchDog extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int code = psData.readByte();
        switch (code) {
            case 1:
            case 3:
            {
                int key = psData.readByte();
                int time = psData.readInt();
                Map<Integer, String> chars = new HashMap<>(Map.ofEntries(Map.entry(38, "↑"), Map.entry(37, "←"), Map.entry(39, "→"), Map.entry(40, "↓"), Map.entry(87, "↑"), Map.entry(68, "→"), Map.entry(65, "←"), Map.entry(83, "↓")));
                gameServer.getSonarPlayerMovement().computeIfAbsent(client.getPlayer().getPlayerName(), k -> new ArrayList<>()).add("<BL>" + chars.getOrDefault(key, "?") + "</BL><G> + <V>" + time + "</V> ms</G>");
                break;
            }
            case 2:
                boolean isJump = psData.readByte() == 1;
                int time = psData.readInt() / 100000;
                gameServer.getSonarPlayerMovement().computeIfAbsent(client.getPlayer().getPlayerName(), k -> new ArrayList<>()).add("<BL>" + (isJump ? "↑" : "↓ (Down)")  + "</BL><G> + <V>" + time + "</V> ms</G>");
                break;
        }
    }
}