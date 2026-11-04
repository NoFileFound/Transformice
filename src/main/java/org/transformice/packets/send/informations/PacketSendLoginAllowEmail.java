package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLoginAllowEmail extends PSSendPacket {
    private final boolean enableEmailLogin;

    /**
     * Creates a new packet of SEND_LOGIN_ALLOW_EMAIL.
     * @param enable Should enable login players using an email address.
     */
    public PacketSendLoginAllowEmail(boolean enable) {
        super(PacketOpcodes.SEND_LOGIN_ALLOW_EMAIL);
        this.enableEmailLogin = enable;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeBoolean(this.enableEmailLogin);
    }
}