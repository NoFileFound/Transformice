package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_SteamUserInfo implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        String userId = data.readString();
        client.closeConnection();
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 12;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}