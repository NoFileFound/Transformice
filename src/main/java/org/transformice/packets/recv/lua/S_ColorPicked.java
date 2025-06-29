package org.transformice.packets.recv.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_ColorPicked implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventColorPicked", data.readInt(), client.getPlayerName(), data.readInt());
        }
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 32;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}