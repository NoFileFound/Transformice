package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_PlayerReport implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseModopwetInstance().createGameReport(data.readString(), data.readByte(), data.readString());
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 25;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}