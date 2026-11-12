package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.SkillType;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanEnableSkill extends PSSendPacket {
    private final SkillType skillType;
    private final int arg;

    /**
     * Creates a new packet of SEND_SHAMAN_ENABLE_SKILL.
     * @param skillType The skill's type.
     * @param arg The skill's argument to set.
     */
    public PacketSendShamanEnableSkill(SkillType skillType, int arg) {
        super(PacketOpcodes.SEND_SHAMAN_ENABLE_SKILL);
        this.skillType = skillType;
        this.arg = arg;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedByte(this.skillType.getId()).writeUnsignedByte(this.arg);
    }
}