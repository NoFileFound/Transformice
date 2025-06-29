package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetNicknameColor implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetNicknameColor(int sessionId, int color) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeInt(color);
    }

    @Override
    public int getC() {
        return 29;
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