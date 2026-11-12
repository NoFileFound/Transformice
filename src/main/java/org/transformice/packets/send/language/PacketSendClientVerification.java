package org.transformice.packets.send.language;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendClientVerification extends PSSendPacket {
    private final int verCode;

    /**
     * Creates a new packet of RECV_CLIENT_VERIFICATION.
     * @param verCode The verification code.
     */
    public PacketSendClientVerification(int verCode) {
        super(PacketOpcodes.SEND_CLIENT_VERIFICATION);
        this.verCode = verCode;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.verCode);
    }
}