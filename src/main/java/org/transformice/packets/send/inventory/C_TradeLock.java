package org.transformice.packets.send.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TradeLock implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TradeLock(int type, boolean isLock) {
        this.byteArray.writeByte(type);
        this.byteArray.writeBoolean(isLock);
    }

    @Override
    public int getC() {
        return 31;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}