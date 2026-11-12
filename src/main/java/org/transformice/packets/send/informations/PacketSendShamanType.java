package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanType extends PSSendPacket {
    private final int shamanType;
    private final boolean canDivine;
    private final int shamanColor;
    private final boolean noSkill;

    /**
     * Creates a new packet of SEND_SHAMAN_TYPE.
     * @param shamanType The shaman mode.
     * @param canDivine Can the shaman use the divine mode.
     * @param shamanColor The shaman's color.
     * @param noSkill Is using the shaman mode without skills.
     */
    public PacketSendShamanType(int shamanType, boolean canDivine, int shamanColor, boolean noSkill) {
        super(PacketOpcodes.SEND_SHAMAN_TYPE);
        this.shamanType = shamanType;
        this.canDivine = canDivine;
        this.shamanColor = shamanColor;
        this.noSkill = noSkill;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.shamanType).writeBoolean(this.canDivine).writeInt(this.shamanColor).writeBoolean(this.noSkill);
    }
}