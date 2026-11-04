package org.transformice.packets.send.language;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendOpenLink extends PSSendPacket {
    private final String url;

    /**
     * Creates a new packet of SEND_OPEN_LINK.
     * @param url The url to open.
     */
    public PacketSendOpenLink(String url) {
        super(PacketOpcodes.SEND_OPEN_LINK);
        this.url = url;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.url, true);
    }
}