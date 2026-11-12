package org.transformice.packets.send.cafe;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCafeBadReputationScore extends PSSendPacket {
    private final short badReputationScore;

    /**
     * Creates a new packet SEND_CAFE_BAD_REPUTATION_SCORE.
     * @param badReputationScore The player's bad cafe reputation.
     */
    public PacketSendCafeBadReputationScore(short badReputationScore) {
        super(PacketOpcodes.SEND_CAFE_BAD_REPUTATION_SCORE);
        this.badReputationScore = badReputationScore;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort(this.badReputationScore);
    }
}