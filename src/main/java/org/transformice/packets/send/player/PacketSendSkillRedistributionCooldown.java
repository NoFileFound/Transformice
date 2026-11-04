package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSkillRedistributionCooldown extends PSSendPacket {
    /**
     * Creates a new packet of SEND_SKILL_REDISTRIBUTION_COOLDOWN.
     */
    public PacketSendSkillRedistributionCooldown() {
        super(PacketOpcodes.SEND_SKILL_REDISTRIBUTION_COOLDOWN);

    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}