package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendHandymouseSkill extends PSSendPacket {
    private final int handyMouseByte;
    private final int objectId;
    private final int nextHandyMouseByte;
    private final int nextObjectId;

    /**
     * Creates a new packet of SEND_HANDYMOUSE_SKILL.
     * @param handyMouseByte The handymouse's byte.
     * @param objectId The object's id.
     * @param nextHandyMouseByte The next handymouse's byte.
     * @param nextObjectId The next object's id.
     */
    public PacketSendHandymouseSkill(int handyMouseByte, int objectId, int nextHandyMouseByte, int nextObjectId) {
        super(PacketOpcodes.SEND_HANDYMOUSE_SKILL);
        this.handyMouseByte = handyMouseByte;
        this.objectId = objectId;
        this.nextHandyMouseByte = nextHandyMouseByte;
        this.nextObjectId = nextObjectId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.handyMouseByte).writeInt(this.objectId).writeByte(this.nextHandyMouseByte).writeInt(this.nextObjectId);
    }
}