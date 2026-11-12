package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRemoveObject extends PSSendPacket {
    private final int objectId;
    private final boolean isRemoved;

    /**
     * Creates a new packet of SEND_REMOVE_OBJECT.
     * @param objectId The object id to remove.
     * @param isRemoved Should remove the object.
     */
    public PacketSendRemoveObject(int objectId, boolean isRemoved) {
        super(PacketOpcodes.SEND_REMOVE_OBJECT);
        this.objectId = objectId;
        this.isRemoved = isRemoved;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.objectId).writeBoolean(this.isRemoved);
    }
}