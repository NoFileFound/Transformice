package org.transformice.packets.send.cafe;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.database.data.CafeEntry;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;
import org.transformice.utils.Utils;

public final class PacketSendCafeOpenTopic extends PSSendPacket {
    private final long topicId;
    private final List<CafeEntry.CafePost> cafePosts;
    private final long playerId;
    private final boolean isUnderModeration;
    private final boolean canReply;
    private final boolean isModerator;

    /**
     * Creates a new packet of SEND_CAFE_OPEN_TOPIC.
     * @param topicId The cafe's topic id.
     * @param posts The cafe's posts.
     * @param playerId The player's id.
     * @param isUnderModeration Is the post under a moderation.
     * @param canReply Can the player reply in the topic.
     * @param isModerator Is the player a moderator.
     */
    public PacketSendCafeOpenTopic(long topicId, List<CafeEntry.CafePost> posts, long playerId, boolean isUnderModeration, boolean canReply, boolean isModerator) {
        super(PacketOpcodes.SEND_CAFE_OPEN_TOPIC);
        this.topicId = topicId;
        this.cafePosts = posts;
        this.playerId = playerId;
        this.isUnderModeration = isUnderModeration;
        this.canReply = canReply;
        this.isModerator = isModerator;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(true);
        packet.writeInt(this.topicId);
        packet.writeBoolean(this.isUnderModeration && this.isModerator);
        packet.writeBoolean(this.canReply);
        for(CafeEntry.CafePost post : this.cafePosts) {
            if(post.state > 0 && !this.isModerator)
                continue;

            packet.writeInt(post.getId());
            packet.writeInt(post.getAuthorId());
            packet.writeInt(Utils.getUnixTime() - post.getDate());
            packet.writeUTF(post.getAuthor(), true);
            packet.writeUTF(post.getMessage(), true);
            packet.writeBoolean(this.playerId != -1 && !post.getVotes().contains(this.playerId));
            packet.writeShort(post.points.shortValue());
            packet.writeUTF((this.isModerator) ? post.moderator : "", true);
            packet.writeByte(post.state);
        }

        return packet;
    }
}