package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRemoveAllObjectsSkill extends PSSendPacket {
    /**
     * Creates a new packet of SEND_REMOVE_ALL_OBJECTS_SKILL.
     */
    public PacketSendRemoveAllObjectsSkill() {
        super(PacketOpcodes.SEND_REMOVE_ALL_OBJECTS_SKILL);

    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}