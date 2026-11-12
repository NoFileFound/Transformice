package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRolloutSkill extends PSSendPacket {
    private final int sessionId;

    /**
     * Creates a new packet of SEND_ROLLOUT_SKILL.
     * @param sessionId The player's session id.
     */
    public PacketSendRolloutSkill(int sessionId) {
        super(PacketOpcodes.SEND_ROLLOUT_SKILL);
        this.sessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId);
    }
}