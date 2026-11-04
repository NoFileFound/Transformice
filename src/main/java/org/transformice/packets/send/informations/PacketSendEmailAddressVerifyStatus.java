package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.EmailAddressRequestType;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendEmailAddressVerifyStatus extends PSSendPacket {
    private final EmailAddressRequestType errorCode;
    private final String emailAddress;

    /**
     * Creates a new packet of SEND_EMAIL_ADDRESS_VERIFY_STATUS.
     * @param errorCode The error code.
     * @param emailAddress The email address.
     */
    public PacketSendEmailAddressVerifyStatus(EmailAddressRequestType errorCode, String emailAddress) {
        super(PacketOpcodes.SEND_EMAIL_ADDRESS_VERIFY_STATUS);
        this.errorCode = errorCode;
        this.emailAddress = emailAddress;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.errorCode.getId());
        packet.writeUTF(this.emailAddress, true);
        return packet;
    }
}