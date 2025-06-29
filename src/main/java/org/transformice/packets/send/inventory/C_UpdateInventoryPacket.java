package org.transformice.packets.send.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_UpdateInventoryPacket implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_UpdateInventoryPacket(int itemId, int quantity) {
        this.byteArray.writeShort((short)itemId);
        this.byteArray.writeUnsignedShort(quantity);
    }

    @Override
    public int getC() {
        return 31;
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