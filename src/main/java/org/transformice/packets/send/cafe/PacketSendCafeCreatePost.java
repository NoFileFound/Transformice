package org.transformice.packets.send.cafe;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCafeCreatePost extends PSSendPacket {
    private final long topicId;
    private final String author;
    private final int posts;

    /**
     * Creates a new packet of SEND_CAFE_CREATE_POST.
     * @param topicId The topic's id.
     * @param author The post's author.
     * @param posts The topic's posts count.
     */
    public PacketSendCafeCreatePost(long topicId, String author, int posts) {
        super(PacketOpcodes.SEND_CAFE_CREATE_POST);
        this.topicId = topicId;
        this.author = author;
        this.posts = posts;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.topicId);
        packet.writeUTF(this.author, true);
        packet.writeInt(this.posts);
        return packet;
    }
}