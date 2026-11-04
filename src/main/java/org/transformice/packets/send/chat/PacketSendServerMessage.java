package org.transformice.packets.send.chat;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendServerMessage extends PSSendPacket {
    private final boolean isTab;
    private final String message;
    private final List<String> args;

    /**
     * Creates a new packet of SEND_SERVER_MESSAGE.
     * @param isTab Should be print tabbed or in #Server.
     * @param message The message.
     */
    public PacketSendServerMessage(boolean isTab, String message) {
        super(PacketOpcodes.SEND_SERVER_MESSAGE);
        this.isTab = isTab;
        this.message = message;
        this.args = List.of();
    }

    /**
     * Creates a new packet of SEND_SERVER_MESSAGE.
     * @param isTab Should be print tabbed or in #Server.
     * @param message The message.
     * @param args The message arguments.
     */
    public PacketSendServerMessage(boolean isTab, String message, List<String> args) {
        super(PacketOpcodes.SEND_SERVER_MESSAGE);
        this.isTab = isTab;
        this.message = message;
        this.args = args;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeBoolean(this.isTab);
        packet.writeUTF(this.message, true);
        packet.writeUnsignedByte(this.args.size());
        for (String arg : this.args)
            packet.writeUTF(arg, true);

        return packet;
    }
}