package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanInfo extends PSSendPacket {
    private final int firstShamanSessionId;
    private final int secondShamanSessionId;
    private final int firstShamanType;
    private final int secondShamanType;
    private final int firstShamanLevel;
    private final int secondShamanLevel;
    private final int firstShamanBadge;
    private final int secondShamanBadge;
    private final boolean firstShamanEnabledSkills;
    private final boolean secondShamanEnabledSkills;

    /**
     * Creates a new packet of SEND_SHAMAN_INFO.
     * @param firstShamanSessionId The first shaman's session id.
     * @param secondShamanSessionId The second shaman's session id.
     * @param firstShamanType The first shaman's type.
     * @param secondShamanType The second shaman's type.
     * @param firstShamanLevel The first shaman's level.
     * @param secondShamanLevel The second shaman's level.
     * @param firstShamanBadge The first shaman's badge.
     * @param secondShamanBadge The second shaman's badge.
     * @param firstShamanEnabledSkills Is the first shaman having skills.
     * @param secondShamanEnabledSkills Is the second shaman having skills.
     */
    public PacketSendShamanInfo(int firstShamanSessionId, int secondShamanSessionId, int firstShamanType, int secondShamanType, int firstShamanLevel, int secondShamanLevel, int firstShamanBadge, int secondShamanBadge, boolean firstShamanEnabledSkills, boolean secondShamanEnabledSkills) {
        super(PacketOpcodes.SEND_SHAMAN_INFO);
        this.firstShamanSessionId = firstShamanSessionId;
        this.secondShamanSessionId = secondShamanSessionId;
        this.firstShamanType = firstShamanType;
        this.secondShamanType = secondShamanType;
        this.firstShamanLevel = (firstShamanEnabledSkills ? firstShamanLevel : 0);
        this.secondShamanLevel = (secondShamanEnabledSkills ? secondShamanLevel : 0);
        this.firstShamanBadge = firstShamanBadge;
        this.secondShamanBadge = secondShamanBadge;
        this.firstShamanEnabledSkills = firstShamanEnabledSkills;
        this.secondShamanEnabledSkills = secondShamanEnabledSkills;
    }



    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.firstShamanSessionId).writeInt(this.secondShamanSessionId).writeByte(this.firstShamanType).writeByte(this.secondShamanType).writeUnsignedShort(this.firstShamanLevel).writeUnsignedShort(this.secondShamanLevel).writeShort((short) this.firstShamanBadge).writeShort((short) this.secondShamanBadge).writeBoolean(!this.firstShamanEnabledSkills).writeBoolean(!this.secondShamanEnabledSkills);
    }
}