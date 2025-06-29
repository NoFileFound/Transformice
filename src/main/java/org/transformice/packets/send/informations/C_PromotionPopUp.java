package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PromotionPopUp implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PromotionPopUp(int categoryId, int itemId, int percentage, int itemBadge) {
        this.byteArray.writeShort((short)categoryId);
        this.byteArray.writeShort((short)itemId);
        this.byteArray.writeByte(percentage);
        this.byteArray.writeShort((short)itemBadge);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}