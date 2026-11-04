package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCampfireSkill extends PSSendPacket {
    private final int posX;
    private final int posY;
    private final int seconds;

    /**
     * Creates a new packet of SEND_CAMPFIRE_SKILL.
     * @param posX The campfire's position on X-axis.
     * @param posY The campfire's position on Y-axis.
     * @param seconds Total seconds until disappear.
     */
    public PacketSendCampfireSkill(int posX, int posY, int seconds) {
        super(PacketOpcodes.SEND_CAMPFIRE_SKILL);
        this.posX = posX;
        this.posY = posY;
        this.seconds = seconds;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.posX).writeShort((short) this.posY).writeByte(this.seconds);
    }
}