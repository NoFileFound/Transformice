package org.transformice.packets.recv.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_VoteCafePost implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseCafeInstance().voteCafePost(data.readInt(), data.readInt(), data.readBoolean());
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 46;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}