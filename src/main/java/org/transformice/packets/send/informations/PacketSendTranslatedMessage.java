package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTranslatedMessage extends PSSendPacket {
    private final String community;
    private final String message;
    private final String[] arguments;

    /**
     * Creates a new packet of SEND_TRANSLATED_MESSAGE.
     * @param community The message's community to be translated.
     * @param message The message to be translated.
     */
    public PacketSendTranslatedMessage(String community, String message) {
        super(PacketOpcodes.SEND_TRANSLATED_MESSAGE);
        this.community = community;
        this.message = message;
        this.arguments = new String[]{};
    }

    /**
     * Creates a new packet of SEND_TRANSLATED_MESSAGE.
     * @param community The message's community to be translated.
     * @param message The message to be translated.
     * @param arguments The message's varargs.
     */
    public PacketSendTranslatedMessage(String community, String message, String[] arguments) {
        super(PacketOpcodes.SEND_TRANSLATED_MESSAGE);
        this.community = community;
        this.message = message;
        this.arguments = arguments;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.community, true);
        packet.writeUTF(this.message, true);
        packet.writeUnsignedByte(this.arguments.length);
        for (String arg : this.arguments)
            packet.writeUTF(arg, true);

        return packet;
    }
}