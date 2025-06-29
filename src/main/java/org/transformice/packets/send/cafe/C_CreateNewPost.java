package org.transformice.packets.send.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CreateNewPost implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CreateNewPost(Long topicId, String author, Integer posts) {
        this.byteArray.writeInt(topicId);
        this.byteArray.writeString(author);
        this.byteArray.writeInt(posts);
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 44;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}