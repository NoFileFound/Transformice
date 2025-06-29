package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DisableProperties implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DisableProperties(boolean disableWatchCommand, boolean disableDebugCommand, boolean disableMinimalistMode) {
        this.byteArray.writeBoolean(disableWatchCommand);
        this.byteArray.writeBoolean(disableDebugCommand);
        this.byteArray.writeBoolean(disableMinimalistMode);
    }

    @Override
    public int getC() {
        return 29;
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