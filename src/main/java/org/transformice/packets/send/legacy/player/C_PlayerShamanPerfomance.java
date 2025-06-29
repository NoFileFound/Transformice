package org.transformice.packets.send.legacy.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerShamanPerfomance implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerShamanPerfomance(String playerName, int amount) {
        this.byteArray.writeString(playerName, false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(String.valueOf(amount), false);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 17;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}