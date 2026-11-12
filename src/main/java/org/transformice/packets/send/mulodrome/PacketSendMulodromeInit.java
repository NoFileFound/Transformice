package org.transformice.packets.send.mulodrome;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMulodromeInit extends PSSendPacket {
    private final boolean isSelf;

    /**
     * Creates a new packet of SEND_MULODROME_INIT.
     * @param isSelf is self.
     */
    public PacketSendMulodromeInit(boolean isSelf) {
        super(PacketOpcodes.SEND_MULODROME_INIT);
        this.isSelf = isSelf;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.isSelf);
    }
}