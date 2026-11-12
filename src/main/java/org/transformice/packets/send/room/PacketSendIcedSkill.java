package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendIcedSkill extends PSSendPacket {
    private final int sessionId;
    private final boolean isEnabled;
    private final boolean showIce;

    /**
     * Creates a new packet of SEND_ICED_SKILL.
     * @param sessionId The player's session id.
     * @param isEnabled Is the player frozen.
     * @param showIce Is the ice will be displayed.
     */
    public PacketSendIcedSkill(int sessionId, boolean isEnabled, boolean showIce) {
        super(PacketOpcodes.SEND_ICED_SKILL);
        this.sessionId = sessionId;
        this.isEnabled = isEnabled;
        this.showIce = showIce;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeBoolean(this.isEnabled).writeBoolean(this.showIce);
    }
}