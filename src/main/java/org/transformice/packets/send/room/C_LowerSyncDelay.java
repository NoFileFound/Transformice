package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LowerSyncDelay implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LowerSyncDelay(int delay) {
        this.byteArray.writeUnsignedShort(delay);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 52;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}