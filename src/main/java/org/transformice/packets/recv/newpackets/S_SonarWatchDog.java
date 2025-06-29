package org.transformice.packets.recv.newpackets;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_SonarWatchDog implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int code = data.readByte();
        switch (code) {
            case 1:
            case 3:
            {
                int key = data.readByte();
                int time = data.readInt();
                Map<Integer, String> chars = new HashMap<>(Map.ofEntries(Map.entry(38, "↑"), Map.entry(37, "←"), Map.entry(39, "→"), Map.entry(40, "↓"), Map.entry(87, "↑"), Map.entry(68, "→"), Map.entry(65, "←"), Map.entry(83, "↓")));
                client.getServer().getSonarPlayerMovement().computeIfAbsent(client.getPlayerName(), k -> new ArrayList<>()).add("<BL>" + chars.getOrDefault(key, "?") + "</BL><G> + <V>" + time + "</V> ms</G>");
                break;
            }
            default:
                Application.getLogger().warn(Application.getTranslationManager().get("invalidsonarcode", code));
        }
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}