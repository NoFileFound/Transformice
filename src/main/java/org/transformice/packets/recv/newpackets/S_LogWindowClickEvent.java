package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_LogWindowClickEvent implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventLogWindowCommand", client.getPlayerName(), data.readString());
        }
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}