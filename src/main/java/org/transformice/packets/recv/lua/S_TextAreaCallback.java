package org.transformice.packets.recv.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_TextAreaCallback implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventTextAreaCallback", data.readInt(), client.getPlayerName(), data.readString());
        }
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}