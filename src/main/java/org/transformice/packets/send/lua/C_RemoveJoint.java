package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RemoveJoint implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RemoveJoint(int jointId) {
        this.byteArray.writeShort((short)jointId);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 31;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}