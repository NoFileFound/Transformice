package org.transformice.packets.send.legacy;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendRoomAnchors extends PSSendPacket {
    private final List<String> anchors;

    /**
     * Creates a new packet of SendRoomAnchors.
     * @param anchors The room anchors.
     */
    public PacketSendRoomAnchors(List<String> anchors) {
        super(LegacyPacketOpcodes.SendRoomAnchors);
        this.anchors = anchors;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(String.join("\u0001", this.anchors), false);
        return packet;
    }
}