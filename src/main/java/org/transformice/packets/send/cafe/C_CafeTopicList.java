package org.transformice.packets.send.cafe;

// Imports
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.database.collections.CafeTopic;
import org.transformice.packets.SendPacket;

public final class C_CafeTopicList implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CafeTopicList(Map<Long, CafeTopic> cafeTopics, boolean canUseCafe, boolean isModerateur) {
        this.byteArray.writeBoolean(canUseCafe);
        this.byteArray.writeBoolean(isModerateur);
        for(CafeTopic cafeTopic : cafeTopics.values()) {
            this.byteArray.writeInt(cafeTopic.getId());
            this.byteArray.writeString(cafeTopic.getTitle());
            this.byteArray.writeInt(cafeTopic.getAuthorId());
            this.byteArray.writeInt(cafeTopic.getPosts().size());
            this.byteArray.writeString(cafeTopic.getPosts().getLast().getAuthor());
            this.byteArray.writeInt(cafeTopic.getDate());
        }
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 40;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}