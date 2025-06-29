package org.transformice.packets.send.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_NewConsumable implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_NewConsumable(int itemId, int quantity) {
        this.byteArray.writeByte(0).writeUnsignedShort(itemId).writeUnsignedShort(quantity);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 67;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}