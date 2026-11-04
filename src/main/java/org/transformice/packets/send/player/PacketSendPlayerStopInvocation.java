package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerStopInvocation extends PSSendPacket {
    private final int sessionId;

    /**
     * Creates a new packet of SEND_PLAYER_STOP_INVOCATION.
     * @param sessionId The player's session id.
     */
    public PacketSendPlayerStopInvocation(int sessionId) {
        super(PacketOpcodes.SEND_PLAYER_STOP_INVOCATION);
        this.sessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId);
    }
}