package org.transformice.packets.recv.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.cafe.C_MulodromeEnd;

@SuppressWarnings("unused")
public final class S_MulodromeClose implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.getRoom().isMulodrome()) return;

        client.getRoom().sendAll(new C_MulodromeEnd());
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 13;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}