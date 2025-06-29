package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerEmoji implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerEmoji(int sessionId, int emojiId) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeUnsignedShort(emojiId);
    }

    @Override
    public int getC() {
        return 8;
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