package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShowColorPicker implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShowColorPicker(int id, int defaultColor, String title) {
        this.byteArray.writeInt(id);
        this.byteArray.writeInt(defaultColor);
        this.byteArray.writeString(title);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 32;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}