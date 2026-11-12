package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendAntigravitySkill extends PSSendPacket {
    private final int objectId;
    private final int arg;

    /**
     * Creates a new packet of SEND_ANTIGRAVITY_SKILL.
     * @param objectId The object's id.
     * @param arg The argument
     */
    public PacketSendAntigravitySkill(int objectId, int arg) {
        super(PacketOpcodes.SEND_ANTIGRAVITY_SKILL);
        this.objectId = objectId;
        this.arg = arg;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.objectId).writeShort((short) this.arg);
    }
}