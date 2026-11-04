package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRequestInfo extends PSSendPacket {
    /**
     * Creates a new packet of SEND_REQUEST_INFO.
     */
    public PacketSendRequestInfo() {
        super(PacketOpcodes.SEND_REQUEST_INFO);
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF("http://51.158.113.197/info.php", true);
    }
}