package org.transformice.packets.send.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DeletePost implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DeletePost(long topicId, long postId) {
        this.byteArray.writeInt(topicId);
        this.byteArray.writeInt(postId);
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 47;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}