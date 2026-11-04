package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomGetCheese extends PSSendPacket {
    private final int sessionId;
    private final int cheeseCount;

    /**
     * Creates a new packet of SEND_ROOM_GET_CHEESE.
     * @param sessionId The session id.
     * @param cheeseCount The session's cheese count.
     */
    public PacketSendRoomGetCheese(int sessionId, int cheeseCount) {
        super(PacketOpcodes.SEND_ROOM_GET_CHEESE);
        this.sessionId = sessionId;
        this.cheeseCount = cheeseCount;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeByte(this.cheeseCount);
    }
}