package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRecyclingSkill extends PSSendPacket {
    private final int jointId;

    /**
     * Creates a new packet of SEND_RECYCLING_SKILL.
     * @param jointId The join's id.
     */
    public PacketSendRecyclingSkill(int jointId) {
        super(PacketOpcodes.SEND_RECYCLING_SKILL);
        this.jointId = jointId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.jointId);
    }
}