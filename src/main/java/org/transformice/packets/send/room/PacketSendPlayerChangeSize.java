package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerChangeSize extends PSSendPacket {
    private final int sessionId;
    private final int size;
    private final boolean excludeShaman;

    /**
     * Creates a new packet of SEND_PLAYER_CHANGE_SIZE.
     * @param sessionId The player's session id.
     * @param size The player's size to change.
     * @param excludeShaman Should exclude the shaman.
     */
    public PacketSendPlayerChangeSize(int sessionId, int size, boolean excludeShaman) {
        super(PacketOpcodes.SEND_PLAYER_CHANGE_SIZE);
        this.sessionId = sessionId;
        this.size = size;
        this.excludeShaman = excludeShaman;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeUnsignedShort(this.size).writeBoolean(this.excludeShaman);
    }
}