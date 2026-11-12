package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendConvertSkill extends PSSendPacket {
    private final int objectId;
    private final boolean isEnabled;

    /**
     * Creates a new packet of SEND_CONVERT_SKILL.
     * @param objectId The object's id.
     * @param isEnabled Is the skill enabled on the object.
     */
    public PacketSendConvertSkill(int objectId, boolean isEnabled) {
        super(PacketOpcodes.SEND_CONVERT_SKILL);
        this.objectId = objectId;
        this.isEnabled = isEnabled;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.objectId).writeBoolean(this.isEnabled);
    }
}