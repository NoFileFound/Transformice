package org.transformice.packets.recv.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.sync.C_ShamanPosition;

@SuppressWarnings("unused")
public final class S_ShamanPosition implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.isShaman) return;

        client.getRoom().sendAll(new C_ShamanPosition(fingerPrint, data.readBoolean()));
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}