package org.transformice.packets.send.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddAnimationLib implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddAnimationLib() {
        this.byteArray.writeInt(0);
        this.byteArray.writeInt(0);
        this.byteArray.writeUnsignedByte(0); /// readUTF()
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 4;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}