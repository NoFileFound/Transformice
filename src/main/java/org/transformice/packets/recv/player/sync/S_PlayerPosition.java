package org.transformice.packets.recv.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.sync.C_PlayerPosition;

@SuppressWarnings("unused")
public final class S_PlayerPosition implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getRoom().sendAll(new C_PlayerPosition(fingerPrint, data.readBoolean()));
    }

    @Override
    public int getC() {
        return 4;
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