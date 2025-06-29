package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LoginSouris implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LoginSouris(int key, int value) {
        this.byteArray.writeUnsignedByte(key);
        this.byteArray.writeUnsignedByte(value);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 33;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}