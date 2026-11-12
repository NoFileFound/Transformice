package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerUnlockBadge extends PSSendPacket {
    private final int sessionId;
    private final int badgeId;

    /**
     * Creates a new packet of SEND_PLAYER_UNLOCK_BADGE.
     * @param sessionId The player's session id.
     * @param badgeId The player's badge id.
     */
    public PacketSendPlayerUnlockBadge(int sessionId, int badgeId) {
        super(PacketOpcodes.SEND_PLAYER_UNLOCK_BADGE);
        this.sessionId = sessionId;
        this.badgeId = badgeId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeShort((short) this.badgeId);
    }
}