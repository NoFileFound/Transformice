package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RemovePhysicObject implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RemovePhysicObject(int imageId) {
        this.byteArray.writeInt128(imageId);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 29;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}