package org.transformice.packets.send;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendStopSonar extends PSSendPacket {
    private final int sessionId;

    /**
     * Creates a new packet of SEND_STOP_SONAR.
     * @param sessionId The player's session id.
     */
    public PacketSendStopSonar(int sessionId) {
        super(PacketOpcodes.SEND_STOP_SONAR);
        this.sessionId = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId);
    }
}