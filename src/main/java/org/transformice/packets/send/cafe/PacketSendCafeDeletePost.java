package org.transformice.packets.send.cafe;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCafeDeletePost extends PSSendPacket {
    private final long topicId;
    private final long postId;

    /**
     * Creates a new packet of SEND_CAFE_DELETE_POST.
     * @param topicId The cafe's topic id.
     * @param postId The cafe's post id.
     */
    public PacketSendCafeDeletePost(long topicId, long postId) {
        super(PacketOpcodes.SEND_CAFE_DELETE_POST);
        this.topicId = topicId;
        this.postId = postId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.topicId).writeInt(this.postId);
    }
}