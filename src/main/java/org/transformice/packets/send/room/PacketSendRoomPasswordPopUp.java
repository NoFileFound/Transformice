package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomPasswordPopUp extends PSSendPacket {
    private final String roomName;

    /**
     * Creates a new packet of SEND_ROOM_PASSWORD_POPUP.
     * @param roomName The room's name.
     */
    public PacketSendRoomPasswordPopUp(String roomName) {
        super(PacketOpcodes.SEND_ROOM_PASSWORD_POPUP);
        this.roomName = roomName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.roomName, true);
    }
}