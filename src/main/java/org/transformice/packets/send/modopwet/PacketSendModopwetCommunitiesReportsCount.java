package org.transformice.packets.send.modopwet;

// Imports
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendModopwetCommunitiesReportsCount extends PSSendPacket {
    private final Map<String, Integer> modoCommunities;

    /**
     * Creates a new packet of SEND_MODOPWET_COMMUNITIES_REPORTS_COUNT.
     * @param modoCommunities The communities.
     */
    public PacketSendModopwetCommunitiesReportsCount(Map<String, Integer> modoCommunities) {
        super(PacketOpcodes.SEND_MODOPWET_COMMUNITIES_REPORTS_COUNT);
        this.modoCommunities = modoCommunities;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUnsignedByte(this.modoCommunities.size());
        for (var entry : this.modoCommunities.entrySet()) {
            packet.writeUTF(entry.getKey(), true);
            packet.writeUnsignedByte(entry.getValue());
        }

        return packet;
    }
}