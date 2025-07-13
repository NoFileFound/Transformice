package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Imports
import org.transformice.packets.send.room.C_LeafSkill;

@SuppressWarnings("unused")
public final class S_LeafSkill implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getRoom().sendAll(new C_LeafSkill(data.readInt(), true));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 33;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}