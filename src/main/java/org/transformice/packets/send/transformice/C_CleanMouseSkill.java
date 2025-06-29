package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CleanMouseSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CleanMouseSkill(int posX) {
        this.byteArray.writeShort((short) posX);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}