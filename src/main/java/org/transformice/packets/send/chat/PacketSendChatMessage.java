package org.transformice.packets.send.chat;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendChatMessage extends PSSendPacket {
    private final String playerName;
    private final String rawMessage;

    /**
     * Creates a new packet of SEND_CHAT_MESSAGE.
     * @param playerName The player's name.
     * @param rawMessage The raw message.
     */
    public PacketSendChatMessage(String playerName, String rawMessage) {
        super(PacketOpcodes.SEND_CHAT_MESSAGE);
        this.playerName = playerName;
        this.rawMessage = rawMessage;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerName, true);
        packet.writeUTF(this.rawMessage, true);
        packet.writeBoolean(false);
        return packet;
    }
}