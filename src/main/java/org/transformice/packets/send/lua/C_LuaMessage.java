package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LuaMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LuaMessage(String message) {
        this.byteArray.writeString(message);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}