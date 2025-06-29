package org.transformice.packets.send.legacy.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerUnlockTitle implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerUnlockTitle(int playerCode, int titleId, int stars) {
        this.byteArray.writeString(String.valueOf(playerCode), false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(titleId), false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(stars), false);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}