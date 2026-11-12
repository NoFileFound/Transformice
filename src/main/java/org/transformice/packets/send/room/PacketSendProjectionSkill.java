package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendProjectionSkill extends PSSendPacket {
    private final int posX;
    private final int posY;
    private final int distance;

    /**
     * Creates a new packet of SEND_PROJECTION_SKILL.
     * @param posX The position's X.
     * @param posY The position's Y.
     * @param distance The projection's distance.
     */
    public PacketSendProjectionSkill(int posX, int posY, int distance) {
        super(PacketOpcodes.SEND_PROJECTION_SKILL);
        this.posX = posX;
        this.posY = posY;
        this.distance = distance;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.posX).writeShort((short) this.posY).writeShort((short) this.distance);
    }
}