package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanExperience extends PSSendPacket {
    private final int level;
    private final int currentXp;
    private final int xpGoal;

    /**
     * Creates a new packet of SEND_SHAMAN_EXPERIENCE.
     * @param level The player's shaman level.
     * @param currentXp The player's total xp.
     * @param xpGoal The player's required xp to next level.
     */
    public PacketSendShamanExperience(int level, int currentXp, int xpGoal) {
        super(PacketOpcodes.SEND_SHAMAN_EXPERIENCE);
        this.level = level;
        this.currentXp = currentXp;
        this.xpGoal = xpGoal;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedShort(this.level).writeInt(this.currentXp).writeInt(this.xpGoal);
    }
}