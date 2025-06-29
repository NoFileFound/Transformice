package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerUnlockBadge implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerUnlockBadge(int sessionId, int badgeId) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeShort((short)badgeId);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 42;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}