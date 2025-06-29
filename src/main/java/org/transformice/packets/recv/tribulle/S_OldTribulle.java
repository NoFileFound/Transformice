package org.transformice.packets.recv.tribulle;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_OldTribulle implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.closeConnection();
    }

    @Override
    public int getC() {
        return 60;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}