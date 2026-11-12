package org.transformice.packets.send.mulodrome;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMulodromeEnd extends PSSendPacket {
    /**
     * Creates a new packet of SEND_MULODROME_END.
     */
    public PacketSendMulodromeEnd() {
        super(PacketOpcodes.SEND_MULODROME_END);
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}