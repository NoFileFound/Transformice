package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomRoundCount extends PSSendPacket {
    private final int roundCount;
    private final int sessionId;

    /**
     * Creates a new packet of SEND_ROOM_ROUND_COUNT.
     * @param roundCount The round's count.
     * @param sessionId The player's session id with highest score.
     */
    public PacketSendRoomRoundCount(int roundCount, int sessionId) {
        super(PacketOpcodes.SEND_ROOM_ROUND_COUNT);
        this.roundCount = roundCount;
        this.sessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.roundCount).writeInt(this.sessionId);
    }
}