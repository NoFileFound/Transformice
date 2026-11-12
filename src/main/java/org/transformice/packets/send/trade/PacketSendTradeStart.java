package org.transformice.packets.send.trade;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTradeStart extends PSSendPacket {
    private final int sessionId;

    /**
     * Creates a new packet of SEND_TRADE_START.
     * @param sessionId The player's session.
     */
    public PacketSendTradeStart(int sessionId) {
        super(PacketOpcodes.SEND_TRADE_START);
        this.sessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId);
    }
}