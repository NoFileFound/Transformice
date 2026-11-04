package org.transformice.packets.send.mulodrome;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMulodromeLeave extends PSSendPacket {
    private final int teamId;
    private final int teamPosition;

    /**
     * Creates a new packet of SEND_MULODROME_LEAVE.
     * @param teamId The team's id.
     * @param teamPosition The team's position.
     */
    public PacketSendMulodromeLeave(int teamId, int teamPosition) {
        super(PacketOpcodes.SEND_MULODROME_LEAVE);
        this.teamId = teamId;
        this.teamPosition = teamPosition;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.teamId).writeByte(this.teamPosition);
    }
}