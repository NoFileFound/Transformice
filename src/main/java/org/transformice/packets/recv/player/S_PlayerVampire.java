package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_PlayerVampire implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.getSessionId() != data.readInt()) return;

        client.sendVampireMode(true);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 66;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}