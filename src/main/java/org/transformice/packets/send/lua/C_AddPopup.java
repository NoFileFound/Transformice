package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddPopup implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddPopup(int popupId, int popupType, String popupText, int posX, int posY, int width, boolean fixedPos) {
        this.byteArray.writeInt(popupId);
        this.byteArray.writeByte(popupType);
        this.byteArray.writeString(popupText);
        this.byteArray.writeInt128(posX);
        this.byteArray.writeInt128(posY);
        this.byteArray.writeInt128(width);
        this.byteArray.writeBoolean(fixedPos);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 23;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}