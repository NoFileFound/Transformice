package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerPosition extends PSSendPacket {
    private final int sessionId;
    private final boolean isFacingRight;

    /**
     * Creates a new packet of SEND_PLAYER_POSITION.
     * @param sessionId The player's session id.
     * @param isFacingRight Is the player facing right.
     */
    public PacketSendPlayerPosition(int sessionId, boolean isFacingRight) {
        super(PacketOpcodes.SEND_PLAYER_POSITION);
        this.sessionId = sessionId;
        this.isFacingRight = isFacingRight;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeBoolean(this.isFacingRight);
    }
}