package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendDemolitionSkill extends PSSendPacket {
    private final int objectId;

    /**
     * Creates a new packet of SEND_DEMOLITION_SKILL.
     * @param objectId The object's id.
     */
    public PacketSendDemolitionSkill(int objectId) {
        super(PacketOpcodes.SEND_DEMOLITION_SKILL);
        this.objectId = objectId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.objectId);
    }
}