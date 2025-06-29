package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetMapName implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetMapName(String mapName) {
        this.byteArray.writeString(mapName);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 25;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}