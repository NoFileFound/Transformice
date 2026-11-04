package org.transformice.packets.send.cafe;

// Imports
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.database.data.CafeEntry;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCafeTopicList extends PSSendPacket {
    private final Map<Long, CafeEntry> cafeTopics;
    private final boolean canUseCafe;
    private final boolean isMod;

    /**
     * Creates a new packet of SEND_CAFE_TOPIC_LIST.
     * @param cafeTopics The cafe topics.
     * @param canUseCafe Can the player create topics and posts in the cafe.
     * @param isMod Is the player a moderator.
     */
    public PacketSendCafeTopicList(Map<Long, CafeEntry> cafeTopics, boolean canUseCafe, boolean isMod) {
        super(PacketOpcodes.SEND_CAFE_TOPIC_LIST);
        this.cafeTopics = cafeTopics;
        this.canUseCafe = canUseCafe;
        this.isMod = isMod;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.canUseCafe);
        packet.writeBoolean(this.isMod);
        for (CafeEntry cafeTopic : this.cafeTopics.values()) {
            packet.writeInt(cafeTopic.getId());
            packet.writeUTF(cafeTopic.getTitle(), true);
            packet.writeInt(cafeTopic.getAuthorId());
            packet.writeInt(cafeTopic.getPosts().size());
            packet.writeUTF(cafeTopic.getPosts().getLast().getAuthor(), true);
            packet.writeInt(cafeTopic.getDate());
        }

        return packet;
    }
}