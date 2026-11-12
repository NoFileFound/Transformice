package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomServerType extends PSSendPacket {
    private final int roomType;

    /**
     * Creates a new packet of SEND_ROOM_SERVER_TYPE.
     * @param roomType The room's type.
     */
    public PacketSendRoomServerType(int roomType) {
        super(PacketOpcodes.SEND_ROOM_SERVER_TYPE);
        this.roomType = roomType;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.roomType);
    }
}