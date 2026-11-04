package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendChangePasswordSuccessPopUp extends PSSendPacket {
    /**
     * Creates a new packet of SEND_CHANGE_PASSWORD_SUCCESS_POPUP.
     */
    public PacketSendChangePasswordSuccessPopUp() {
        super(PacketOpcodes.SEND_CHANGE_PASSWORD_SUCCESS_POPUP);
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}