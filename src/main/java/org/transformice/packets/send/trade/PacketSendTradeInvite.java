package org.transformice.packets.send.trade;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTradeInvite extends PSSendPacket {
    private final int sessionId;

    /**
     * Creates a new packet of SEND_TRADE_INVITE.
     * @param sessionId The player's session id.
     */
    public PacketSendTradeInvite(int sessionId) {
        super(PacketOpcodes.SEND_TRADE_INVITE);
        this.sessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId);
    }
}