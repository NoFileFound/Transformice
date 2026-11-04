package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanEarnedExperience extends PSSendPacket {
    private final int currentXp;
    private final int levelXp;

    /**
     * Creates a new packet of SEND_SHAMAN_EARNED_EXPERIENCE.
     * @param currentXp The shaman's current xp.
     * @param levelXp The shaman's level xp.
     */
    public PacketSendShamanEarnedExperience(int currentXp, int levelXp) {
        super(PacketOpcodes.SEND_SHAMAN_EARNED_EXPERIENCE);
        this.currentXp = currentXp;
        this.levelXp = levelXp;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.currentXp).writeShort((short) this.levelXp);
    }
}