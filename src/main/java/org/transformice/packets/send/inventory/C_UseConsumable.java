package org.transformice.packets.send.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_UseConsumable implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_UseConsumable(int sessionId, short itemId) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeShort(itemId);
    }

    @Override
    public int getC() {
        return 31;
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