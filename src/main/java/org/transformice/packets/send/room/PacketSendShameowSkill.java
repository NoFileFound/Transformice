package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShameowSkill extends PSSendPacket {
    private final int sessionId;
    private final boolean isEnabled;

    /**
     * Creates a new packet of SEND_SHAMEOW_SKILL.
     * @param sessionId The player's session id.
     * @param isEnabled Is the shameow skill enabled.
     */
    public PacketSendShameowSkill(int sessionId, boolean isEnabled) {
        super(PacketOpcodes.SEND_SHAMEOW_SKILL);
        this.sessionId = sessionId;
        this.isEnabled = isEnabled;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeBoolean(this.isEnabled);
    }
}