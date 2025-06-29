package org.transformice.packets.recv.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_KeyBoard implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        data.decryptPacket(Application.getSwfInfo().packet_keys, fingerPrint);
        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventKeyboard", client.getPlayerName(), data.readInt128(), data.readBoolean(), data.readInt128(), data.readInt128(), data.readInt128() * 10, data.readInt128() * 10);
        }
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}