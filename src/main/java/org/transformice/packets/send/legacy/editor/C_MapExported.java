package org.transformice.packets.send.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MapExported implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MapExported(int mapCode) {
        this.byteArray.writeString(String.valueOf(mapCode), false);
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}