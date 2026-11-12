package org.transformice.packets.send;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTransformation extends PSSendPacket {
    private final int sessionId;
    private final short transformType;

    /**
     * Creates a new packet of SEND_TRANSFORMATION.
     * @param sessionId The player's session id.
     * @param transformType The transformation type.
     */
    public PacketSendTransformation(int sessionId, short transformType) {
        super(PacketOpcodes.SEND_TRANSFORMATION);
        this.sessionId = sessionId;
        this.transformType = transformType;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeShort(this.transformType);
    }
}