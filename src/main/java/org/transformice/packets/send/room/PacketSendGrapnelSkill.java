package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendGrapnelSkill extends PSSendPacket {
    private final int sessionId;
    private final int posX;
    private final int posY;

    /**
     * Creates a new packet of SEND_GRAPNEL_SKILL.
     * @param sessionId The player's session id.
     * @param posX The player's position on X-axis.
     * @param posY The player's position on Y-axis.
     */
    public PacketSendGrapnelSkill(int sessionId, int posX, int posY) {
        super(PacketOpcodes.SEND_GRAPNEL_SKILL);
        this.sessionId = sessionId;
        this.posX = posX;
        this.posY = posY;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeShort((short) this.posX).writeShort((short) this.posY);
    }
}