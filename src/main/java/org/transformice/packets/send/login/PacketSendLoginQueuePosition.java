package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLoginQueuePosition extends PSSendPacket {
    private final int position;

    /**
     * Creates a new packet of SEND_LOGIN_QUEUE_POSITION.
     * @param position The position.
     */
    public PacketSendLoginQueuePosition(int position) {
        super(PacketOpcodes.SEND_LOGIN_QUEUE_POSITION);
        this.position = position;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedInt(this.position);
    }
}