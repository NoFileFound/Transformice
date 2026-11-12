package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendVerifyIdentityUsernameEmailAddress extends PSSendPacket {
    private final int status;

    /**
     * Creates a new packet of SEND_VERIFY_IDENTITY_USERNAME_EMAIL_ADDRESS_REQUEST.
     * @param status The error's status.
     */
    public PacketSendVerifyIdentityUsernameEmailAddress(int status) {
        super(PacketOpcodes.SEND_VERIFY_IDENTITY_USERNAME_EMAIL_ADDRESS_REQUEST);
        this.status = status;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.status);
    }
}