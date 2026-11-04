package org.transformice.packets.send.mulodrome;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMulodromeResult extends PSSendPacket {
    private final int mulodromeRoundCount;
    private final int redTeamCount;
    private final int blueTeamCount;

    /**
     * Creates a new packet of SEND_MULODROME_RESULT.
     * @param mulodromeRoundCount The total passed rounds.
     * @param blueTeamCount The blue's team count.
     * @param redTeamCount The red's team account.
     */
    public PacketSendMulodromeResult(int mulodromeRoundCount, int blueTeamCount, int redTeamCount) {
        super(PacketOpcodes.SEND_MULODROME_RESULT);
        this.mulodromeRoundCount = mulodromeRoundCount;
        this.blueTeamCount = blueTeamCount;
        this.redTeamCount = redTeamCount;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.mulodromeRoundCount).writeShort((short) this.blueTeamCount).writeShort((short) this.redTeamCount);
    }
}