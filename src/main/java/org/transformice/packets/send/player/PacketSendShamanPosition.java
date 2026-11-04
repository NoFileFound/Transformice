package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanPosition extends PSSendPacket {
    private final int sessionId;
    private final boolean direction;

    /**
     * Creates a new packet of SEND_SHAMAN_POSITION.
     * @param sessionId The session's id.
     * @param direction The shaman's direction (left or right).
     */
    public PacketSendShamanPosition(int sessionId, boolean direction) {
        super(PacketOpcodes.SEND_SHAMAN_POSITION);
        this.sessionId = sessionId;
        this.direction = direction;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeBoolean(this.direction);
    }
}