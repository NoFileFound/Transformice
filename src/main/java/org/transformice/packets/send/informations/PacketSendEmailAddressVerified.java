package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendEmailAddressVerified extends PSSendPacket {
    private final String emailAddress;
    private final boolean isVerified;

    /**
     * Creates a new packet of SEND_EMAIL_ADDRESS_VERIFIED.
     * @param emailAddress The email address.
     * @param isVerified Is the email address verified before.
     */
    public PacketSendEmailAddressVerified(String emailAddress, boolean isVerified) {
        super(PacketOpcodes.SEND_EMAIL_ADDRESS_VERIFIED);
        this.emailAddress = emailAddress;
        this.isVerified = isVerified;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.isVerified);
        packet.writeUTF(this.emailAddress, true);
        return packet;
    }
}