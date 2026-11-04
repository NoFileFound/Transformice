package org.transformice.packets.send.informations;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLogMessage extends PSSendPacket {
    private final int fontType;
    private final String message;

    /**
     * Creates a new packet of SEND_LOG_MESSAGE.
     * @param fontType The font's type.
     * @param message The message.
     */
    public PacketSendLogMessage(int fontType, String message) {
        super(PacketOpcodes.SEND_LOG_MESSAGE);
        this.fontType = fontType;
        this.message = message;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.fontType);
        packet.writeUTF("", true);
        packet.writeUnsignedByte((this.message.length() >> 16) & 0xFF);
        packet.writeUnsignedByte((this.message.length() >> 8) & 0xFF);
        packet.writeUnsignedByte((this.message.length() & 0xFF));
        packet.writeBytes(this.message.getBytes());
        return packet;
    }
}