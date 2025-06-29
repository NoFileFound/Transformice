package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_BindKeyboard implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_BindKeyboard(int keyCode, boolean down, boolean active) {
        this.byteArray.writeShort((short) keyCode);
        this.byteArray.writeBoolean(down);
        this.byteArray.writeBoolean(active);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}