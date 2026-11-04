package org.transformice.packets.send.mulodrome;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMulodromeWinner extends PSSendPacket {
    private final int blueTeamCount;
    private final int redTeamCount;

    /**
     * Creates a new packet of SEND_MULODROME_WINNER.
     * @param blueTeamCount The blue's team count.
     * @param redTeamCount The red's team count.
     */
    public PacketSendMulodromeWinner(int blueTeamCount, int redTeamCount) {
        super(PacketOpcodes.SEND_MULODROME_WINNER);
        this.blueTeamCount = blueTeamCount;
        this.redTeamCount = redTeamCount;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        int winnerType = (this.blueTeamCount == this.redTeamCount ? 2 : (this.blueTeamCount < this.redTeamCount ? 1 : 0));
        return new PSByteArray().writeByte(winnerType).writeShort((short) this.blueTeamCount).writeShort((short) this.redTeamCount);
    }
}