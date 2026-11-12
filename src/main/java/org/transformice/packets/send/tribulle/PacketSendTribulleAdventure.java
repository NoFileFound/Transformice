package org.transformice.packets.send.tribulle;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTribulleAdventure extends PSSendPacket {
    private final int bannerId;

    /**
     * Creates a new packet of SEND_TRIBULLE_ADVENTURE.
     * @param bannerId The adventure's id.
     */
    public PacketSendTribulleAdventure(int bannerId) {
        super(PacketOpcodes.SEND_TRIBULLE_ADVENTURE);
        this.bannerId = bannerId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedByte(1).writeUnsignedByte(this.bannerId).writeBoolean(true).writeBoolean(true);
    }
}