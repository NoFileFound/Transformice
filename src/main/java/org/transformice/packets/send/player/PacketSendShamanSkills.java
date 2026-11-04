package org.transformice.packets.send.player;

// Imports
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.SkillType;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanSkills extends PSSendPacket {
    private final Map<SkillType, Integer> playerSkills;
    private final boolean refresh;

    /**
     * Creates a new packet of SEND_SHAMAN_SKILLS.
     * @param playerSkills The player's skills.
     * @param refresh Should refresh the skills in the skill tree.
     */
    public PacketSendShamanSkills(Map<SkillType, Integer> playerSkills, boolean refresh) {
        super(PacketOpcodes.SEND_SHAMAN_SKILLS);
        this.playerSkills = playerSkills;
        this.refresh = refresh;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.playerSkills.size());
        for(var skill : this.playerSkills.entrySet()) {
            packet.writeByte(skill.getKey().getId());
            packet.writeByte(skill.getValue());
        }

        packet.writeBoolean(refresh);
        return packet;
    }
}