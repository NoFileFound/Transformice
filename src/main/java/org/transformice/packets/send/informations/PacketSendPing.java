package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPing extends PSSendPacket {
    private final int payload;
    private final boolean isMainServer;

    /**
     * Creates a new packet of SEND_PING.
     * @param payload The payload.
     * @param isMainServer is pinging the main server or a bulle.
     */
    public PacketSendPing(int payload, boolean isMainServer) {
        super(PacketOpcodes.SEND_PING);
        this.payload = payload;
        this.isMainServer = isMainServer;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.payload).writeBoolean(this.isMainServer);
    }
}