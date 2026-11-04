package org.transformice.packets.send.legacy;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class PacketSendPlayerDisconnect extends PSSendPacket {
    private final int session;

    /**
     * Creates a new packet of SendPlayerDisconnect.
     * @param sessionId The player's session id.
     */
    public PacketSendPlayerDisconnect(int sessionId) {
        super(LegacyPacketOpcodes.SendPlayerDisconnect);
        this.session = sessionId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(String.valueOf(this.session), false);
    }
}