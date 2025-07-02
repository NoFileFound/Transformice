package org.transformice.packets.send.legacy.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_OldIgnoreCommand implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OldIgnoreCommand(String playerName) {
        this.byteArray.writeString(playerName);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 19;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}