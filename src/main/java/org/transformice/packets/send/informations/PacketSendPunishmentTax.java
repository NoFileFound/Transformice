package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPunishmentTax extends PSSendPacket {
    private final int taxCount;

    /**
     * Creates a packet of SEND_PUNISHMENT_TAX.
     * @param taxCount The tax count.
     */
    public PacketSendPunishmentTax(int taxCount) {
        super(PacketOpcodes.SEND_PUNISHMENT_TAX);
        this.taxCount = taxCount;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.taxCount);
    }
}