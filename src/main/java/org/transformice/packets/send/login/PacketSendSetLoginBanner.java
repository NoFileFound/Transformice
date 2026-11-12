package org.transformice.packets.send.login;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSetLoginBanner extends PSSendPacket {
    private final String background;
    private final String foreground;

    /**
     * Creates a new packet of SEND_LOGIN_SET_BANNER.
     * @param background The banner's background image name.
     * @param foreground The banner's foreground image name.
     */
    public PacketSendSetLoginBanner(String background, String foreground) {
        super(PacketOpcodes.SEND_LOGIN_SET_BANNER);
        this.background = background;
        this.foreground = foreground;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.background + "#" + this.foreground + "#0#0", true);
    }
}