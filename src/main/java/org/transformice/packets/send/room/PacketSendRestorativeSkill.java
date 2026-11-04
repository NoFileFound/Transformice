package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRestorativeSkill extends PSSendPacket {
    private final int objectId;
    private final int id;

    /**
     * Creates a new packet of SEND_RESTORATIVE_SKILL.
     * @param objectId The object's id.
     * @param id The restoration id.
     */
    public PacketSendRestorativeSkill(int objectId, int id) {
        super(PacketOpcodes.SEND_RESTORATIVE_SKILL);
        this.objectId = objectId;
        this.id = id;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.objectId).writeInt(this.id);
    }
}