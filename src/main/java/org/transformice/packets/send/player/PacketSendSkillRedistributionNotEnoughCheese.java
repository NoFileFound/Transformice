package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSkillRedistributionNotEnoughCheese extends PSSendPacket {
    /**
     * Creates a new packet of SEND_SKILL_REDISTRIBUTION_NOT_ENOUGH_CHEESE.
     */
    public PacketSendSkillRedistributionNotEnoughCheese() {
        super(PacketOpcodes.SEND_SKILL_REDISTRIBUTION_NOT_ENOUGH_CHEESE);

    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}