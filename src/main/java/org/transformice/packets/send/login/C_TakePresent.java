package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TakePresent implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TakePresent(int x, int y) {
        this.byteArray.writeShort((short)x);
        this.byteArray.writeShort((short)y);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}