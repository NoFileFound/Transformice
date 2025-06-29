package org.transformice.packets.send.modopwet;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_WatchPlayer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_WatchPlayer(String playerName) {
        this.byteArray.writeString(playerName);
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 11;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}