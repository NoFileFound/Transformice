package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerMeep implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerMeep(int sessionId, int x, int y, int power) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeShort((short)x);
        this.byteArray.writeShort((short)y);
        this.byteArray.writeInt(power);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 38;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}