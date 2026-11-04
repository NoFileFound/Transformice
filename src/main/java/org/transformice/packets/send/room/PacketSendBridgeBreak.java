package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendBridgeBreak extends PSSendPacket {
    private final short bridgeCode;

    /**
     * Creates a new packet of SEND_BRIDGE_BREAK.
     * @param bridgeCode The bridge's code.
     */
    public PacketSendBridgeBreak(short bridgeCode) {
        super(PacketOpcodes.SEND_BRIDGE_BREAK);
        this.bridgeCode = bridgeCode;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort(this.bridgeCode);
    }
}