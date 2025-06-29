package org.transformice.packets.recv.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_DeleteCafePost implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseCafeInstance().sendDeleteCafePost(data.readInt());
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 47;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}