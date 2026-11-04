package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLaunchHotAirBalloonWithBadge extends PSSendPacket {
    private final int sessionId;
    private final int badgeId;

    /**
     * Creates a new packet of SEND_LAUNCH_HOT_AIR_BALLOON_WITH_BADGE.
     * @param sessionId The player's session id.
     * @param badgeId The badge's id.
     */
    public PacketSendLaunchHotAirBalloonWithBadge(int sessionId, int badgeId) {
        super(PacketOpcodes.SEND_LAUNCH_HOT_AIR_BALLOON_WITH_BADGE);
        this.sessionId = sessionId;
        this.badgeId = badgeId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeUnsignedShort(this.badgeId);
    }
}