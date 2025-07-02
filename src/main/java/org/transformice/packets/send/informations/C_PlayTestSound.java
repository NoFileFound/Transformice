package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayTestSound implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayTestSound(String langue, String ue) {
        this.byteArray.writeString(langue).writeString(ue);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 51;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}