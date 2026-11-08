package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerSetScore extends PSSendPacket {
    private final int sessionId;
    private final int score;

    /**
     * Creates a new packet of SEND_PLAYER_SET_SCORE.
     * @param sessionId The player's session id.
     * @param score The player's score.
     */
    public PacketSendPlayerSetScore(int sessionId, int score) {
        super(PacketOpcodes.SEND_PLAYER_SET_SCORE);
        this.sessionId = sessionId;
        this.score = score;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeShort((short) this.score);
    }
}