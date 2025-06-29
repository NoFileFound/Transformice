package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_HandleReportedPost implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseCafeInstance().handleReportedPost(data.readInt(), data.readBoolean());
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}