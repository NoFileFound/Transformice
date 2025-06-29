package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SummonEventElement implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SummonEventElement(int actionType) {
        this.byteArray.writeShort((short)actionType);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 44;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}