package org.transformice.packets.send.cafe;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.database.embeds.CafePost;
import org.transformice.packets.SendPacket;

public final class C_ShowTopicPosts implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShowTopicPosts(Long topicId, List<CafePost> posts, Long playerId, boolean isUnderModeration, boolean canReply, boolean isModerator) {
        this.byteArray.writeBoolean(true);
        this.byteArray.writeInt(topicId);
        this.byteArray.writeBoolean(isUnderModeration && isModerator);
        this.byteArray.writeBoolean(canReply);
        for(CafePost post : posts) {
            if(post.getState() > 0 && !isModerator) continue;

            this.byteArray.writeInt(post.getId());
            this.byteArray.writeInt(post.getAuthorId());
            this.byteArray.writeInt(getUnixTime() - post.getDate());
            this.byteArray.writeString(post.getAuthor());
            this.byteArray.writeString(post.getMessage());
            this.byteArray.writeBoolean(playerId != -1 && !post.getVotes().contains(playerId));
            this.byteArray.writeShort(post.getPoints());
            this.byteArray.writeString((isModerator) ? post.getModerator() : "");
            this.byteArray.writeByte(post.getState());
        }
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 41;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}