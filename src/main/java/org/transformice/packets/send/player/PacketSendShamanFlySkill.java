package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanFlySkill extends PSSendPacket {
    private final int sessionId;
    private final boolean isFlying;

    /**
     * Creates a new packet of SEND_SHAMAN_FLY_SKILL.
     * @param sessionId The player's session id.
     * @param isFlying Is the player flying right now.
     */
    public PacketSendShamanFlySkill(int sessionId, boolean isFlying) {
        super(PacketOpcodes.SEND_SHAMAN_FLY_SKILL);
        this.sessionId = sessionId;
        this.isFlying = isFlying;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeBoolean(this.isFlying);
    }
}