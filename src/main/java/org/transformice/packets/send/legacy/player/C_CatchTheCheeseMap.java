package org.transformice.packets.send.legacy.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CatchTheCheeseMap implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CatchTheCheeseMap(int shamanSessionId) {
        this.byteArray.writeString(String.valueOf(shamanSessionId), false);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 23;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}