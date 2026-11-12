package org.transformice.packets.send.cafe;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendOpenCafe extends PSSendPacket {
    private final boolean canAccessCafe;

    /**
     * Creates a new packet of SEND_OPEN_CAFE.
     * @param canAccessCafe Can the player create new cafe posts or topics.
     */
    public PacketSendOpenCafe(boolean canAccessCafe) {
        super(PacketOpcodes.SEND_OPEN_CAFE);
        this.canAccessCafe = canAccessCafe;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.canAccessCafe);
    }
}