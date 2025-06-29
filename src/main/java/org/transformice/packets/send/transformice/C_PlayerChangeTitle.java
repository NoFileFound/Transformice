package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerChangeTitle implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerChangeTitle(int gender, short titleId) {
        this.byteArray.writeByte(gender);
        this.byteArray.writeShort(titleId);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 72;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}