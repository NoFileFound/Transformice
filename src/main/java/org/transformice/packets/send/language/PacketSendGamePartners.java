package org.transformice.packets.send.language;

// Imports
import java.util.List;
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendGamePartners extends PSSendPacket {
    private final List<Map<String, String>> partners;

    /**
     * Creates a new packet of SEND_GAME_PARTNERS.
     * @param partners The partner list.
     */
    public PacketSendGamePartners(List<Map<String, String>> partners) {
        super(PacketOpcodes.SEND_GAME_PARTNERS);
        this.partners = partners;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUnsignedShort(this.partners.size());
        for (Map<String, String> partner : this.partners) {
            packet.writeUTF(partner.get("name"), true);
            packet.writeUTF(partner.get("url"), true);
        }

        return packet;
    }
}