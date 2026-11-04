package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomType extends PSSendPacket {
    private final int roomType;

    /**
     * Creates a new packet of SEND_ROOM_TYPE.
     * @param roomType The room's type.
     */
    public PacketSendRoomType(int roomType) {
        super(PacketOpcodes.SEND_ROOM_TYPE);
        this.roomType = roomType;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.roomType);
    }
}