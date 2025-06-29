package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddTextArea implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddTextArea(int txtAreaId, String text, int posX, int posY, int width, int height, int backgroundColor, int borderColor, int backgroundAlpha, boolean fixedPos) {
        this.byteArray.writeInt(txtAreaId);
        this.byteArray.writeString(text);
        this.byteArray.writeInt128(posX);
        this.byteArray.writeInt128(posY);
        this.byteArray.writeInt128(width);
        this.byteArray.writeInt128(height);
        this.byteArray.writeInt(backgroundColor);
        this.byteArray.writeInt(borderColor);
        this.byteArray.writeByte(backgroundAlpha);
        this.byteArray.writeBoolean(fixedPos);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}