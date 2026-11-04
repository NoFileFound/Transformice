package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSpiderMouseSkill extends PSSendPacket {
    private final int posX;
    private final int posY;

    /**
     * Creates a new packet of SEND_SPIDER_MOUSE_SKILL.
     * @param posX The position of X.
     * @param posY The position of Y.
     */
    public PacketSendSpiderMouseSkill(int posX, int posY) {
        super(PacketOpcodes.SEND_SPIDER_MOUSE_SKILL);
        this.posX = posX;
        this.posY = posY;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.posX).writeShort((short) this.posY);
    }
}