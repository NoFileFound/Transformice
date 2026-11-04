package org.transformice.packets.send.trade;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTradeComplete extends PSSendPacket {
    /**
     * Creates a new packet of SEND_TRADE_COMPLETE.
     */
    public PacketSendTradeComplete() {
        super(PacketOpcodes.SEND_TRADE_COMPLETE);

    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}