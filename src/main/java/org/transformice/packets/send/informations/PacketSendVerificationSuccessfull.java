package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendVerificationSuccessfull extends PSSendPacket {
    private final boolean isSuccess;

    /**
     * Creates a new packet of SEND_EMAIL_VERIFICATION_SUCCESSFULL.
     * @param isSuccess Is the email verification successfull.
     */
    public PacketSendVerificationSuccessfull(boolean isSuccess) {
        super(PacketOpcodes.SEND_EMAIL_VERIFICATION_SUCCESSFULL);
        this.isSuccess = isSuccess;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.isSuccess);
    }
}