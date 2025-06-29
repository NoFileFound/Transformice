package org.transformice.packets.send.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_OpenCafe implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OpenCafe(boolean canCreateTopics) {
        this.byteArray.writeBoolean(canCreateTopics);
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 42;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}