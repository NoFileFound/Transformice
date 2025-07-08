package org.transformice.packets.recv.tribe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@Deprecated
@SuppressWarnings("unused")
public final class S_ReturnToElection implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.closeConnection();
    }

    @Override
    public int getC() {
        return 16;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}