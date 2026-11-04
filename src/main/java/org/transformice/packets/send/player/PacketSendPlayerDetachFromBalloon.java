package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerDetachFromBalloon extends PSSendPacket {
    private final int sessionId;

    /**
     * Creates a new packet of SEND_PLAYER_DETACH_FROM_BALLOON.
     * @param sessionId The player's session id.
     */
    public PacketSendPlayerDetachFromBalloon(int sessionId) {
        super(PacketOpcodes.SEND_PLAYER_DETACH_FROM_BALLOON);
        this.sessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId);
    }
}