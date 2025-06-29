package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetPlayerHealth implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetPlayerHealth(int health) {
        this.byteArray.writeByte(health);
    }

    @Override
    public int getC() {
        return 26;
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