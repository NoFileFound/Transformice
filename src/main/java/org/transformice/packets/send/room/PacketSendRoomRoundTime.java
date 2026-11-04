package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomRoundTime extends PSSendPacket {
    private final short roundTime;

    /**
     * Creates a new packet of SEND_ROOM_ROUND_TIME.
     * @param roundTime The round's start time.
     */
    public PacketSendRoomRoundTime(short roundTime) {
        super(PacketOpcodes.SEND_ROOM_ROUND_TIME);
        this.roundTime = roundTime;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort(this.roundTime);
    }
}