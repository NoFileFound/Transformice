package org.transformice.packets.send.legacy.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_InitAvatar implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_InitAvatar(int avatarId) {
        this.byteArray.writeInt(avatarId);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 24;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}