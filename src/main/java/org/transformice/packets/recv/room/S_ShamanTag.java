package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_ShamanTag;

@SuppressWarnings("unused")
public final class S_ShamanTag implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.isShaman) return;

        client.getRoom().sendAll(new C_ShamanTag(data.readByte(), data.readShort(), data.readShort()));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}