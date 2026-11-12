package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendStaffServerPing extends PSSendPacket {
    /**
     * Creates a new value of SEND_STAFF_SERVER_PING.
     */
    public PacketSendStaffServerPing() {
        super(PacketOpcodes.SEND_STAFF_SERVER_PING);
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}