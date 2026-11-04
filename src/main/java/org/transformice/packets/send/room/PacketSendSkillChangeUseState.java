package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSkillChangeUseState extends PSSendPacket {
    private final int skillId;
    private final int stateId;

    /**
     * Creates a new packet of SEND_SKILL_CHANGE_USE_STATE.
     * @param skillId The skill's id.
     * @param stateId The state's id.
     */
    public PacketSendSkillChangeUseState(int skillId, int stateId) {
        super(PacketOpcodes.SEND_SKILL_CHANGE_USE_STATE);
        this.skillId = skillId;
        this.stateId = stateId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.skillId).writeByte(this.stateId);
    }
}