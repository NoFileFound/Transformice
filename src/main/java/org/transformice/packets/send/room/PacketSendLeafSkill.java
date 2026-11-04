package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLeafSkill extends PSSendPacket {
    private final boolean isEnabled;
    private final int sessionId;

    /**
     * Creates a new packet of SEND_LEAF_SKILL.
     * @param isEnabled Is the leaf enabled on the player.
     * @param sessionId The player's session id.
     */
    public PacketSendLeafSkill(boolean isEnabled, int sessionId) {
        super(PacketOpcodes.SEND_LEAF_SKILL);
        this.isEnabled = isEnabled;
        this.sessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.isEnabled).writeInt(this.sessionId);
    }
}