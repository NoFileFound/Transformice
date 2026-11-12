package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLoginFlyerPopUp extends PSSendPacket {
    private final String flyerName;

    /**
     * Creates a new packet of SEND_LOGIN_FLYER_POPUP.
     * @param flyerName The flyer's name.
     */
    public PacketSendLoginFlyerPopUp(String flyerName) {
        super(PacketOpcodes.SEND_LOGIN_FLYER_POPUP);
        this.flyerName = flyerName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.flyerName, true);
    }
}