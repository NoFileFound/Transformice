package org.transformice.packets.send.chat;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendHtmlMessage extends PSSendPacket {
    private final String message;

    /**
     * Creates a new packet of SEND_HTML_MESSAGE.
     * @param message The message.
     */
    public PacketSendHtmlMessage(String message) {
        super(PacketOpcodes.SEND_HTML_MESSAGE);
        this.message = message;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.message, true);
    }
}