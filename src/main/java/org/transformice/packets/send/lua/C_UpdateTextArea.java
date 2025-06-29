package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_UpdateTextArea implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_UpdateTextArea(int textAreaId, String text) {
        this.byteArray.writeInt(textAreaId);
        this.byteArray.writeString(text);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}