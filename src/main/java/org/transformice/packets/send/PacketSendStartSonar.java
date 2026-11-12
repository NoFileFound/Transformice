package org.transformice.packets.send;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendStartSonar extends PSSendPacket {
    private final int sessionId;
    private final boolean isEnabled;
    private final int sonarCode;

    /**
     * Creates a new packet of SEND_START_SONAR.
     * @param sessionId The session's id.
     * @param isEnabled Is sonar enabled.
     * @param sonarCode The sonar's code.
     */
    public PacketSendStartSonar(int sessionId, boolean isEnabled, int sonarCode) {
        super(PacketOpcodes.SEND_START_SONAR);
        this.sessionId = sessionId;
        this.isEnabled = isEnabled;
        this.sonarCode = sonarCode;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeBoolean(this.isEnabled).writeShort((short) this.sonarCode);
    }
}