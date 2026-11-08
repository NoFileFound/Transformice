package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendTribeMusique extends PSSendPacket {
    private final String musicUrl;

    /**
     * Creates a new packet of SEND_TRIBE_MUSIQUE.
     * @param musicUrl The music's url.
     */
    public PacketSendTribeMusique(String musicUrl) {
        super(LegacyPacketOpcodes.SendTribeHouseMusic);
        this.musicUrl = musicUrl;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        if (this.musicUrl.isEmpty()) {
            packet.writeByte(0);
        } else {
            packet.writeUTF(this.musicUrl, false);
        }

        return packet;
    }
}