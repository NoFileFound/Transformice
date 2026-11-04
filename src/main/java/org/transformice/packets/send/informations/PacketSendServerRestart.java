package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendServerRestart extends PSSendPacket {
    private final int seconds;

    /**
     * Creates a new packet of SEND_SERVER_RESTART.
     * @param seconds The remaining seconds.
     */
    public PacketSendServerRestart(int seconds) {
        super(PacketOpcodes.SEND_SERVER_RESTART);
        this.seconds = seconds;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.seconds * 1000);
    }
}