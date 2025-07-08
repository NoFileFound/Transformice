package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetAchievement implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetAchievement(String data) {
        this.byteArray.writeString(data);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 13;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}