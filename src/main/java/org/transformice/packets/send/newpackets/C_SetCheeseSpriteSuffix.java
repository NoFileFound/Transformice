package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SetCheeseSpriteSuffix implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetCheeseSpriteSuffix(String suffix) {
        this.byteArray.writeString(suffix);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 39;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}