package org.transformice.packets.send.modopwet;

// Imports
import java.util.Deque;
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendModopwetChatLog extends PSSendPacket {
    private final String playerName;
    private final Map<String, Deque<String[]>> chatMessages;
    private final Map<String, Deque<String[]>> whisperMessages;

    /**
     * Creates a new packet of SEND_MODOPWET_CHAT_LOG.
     * @param playerName The player's name.
     * @param chatMessages The player's chat messages.
     * @param whisperMessages The player's whispered messages.
     */
    public PacketSendModopwetChatLog(String playerName, Map<String, Deque<String[]>> chatMessages, Map<String, Deque<String[]>> whisperMessages) {
        super(PacketOpcodes.SEND_MODOPWET_CHAT_LOG);
        this.playerName = playerName;
        this.chatMessages = chatMessages;
        this.whisperMessages = whisperMessages;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerName, true);
        packet.writeByte(this.chatMessages.size());
        for (var entry : this.chatMessages.entrySet()) {
            packet.writeUTF(entry.getKey(), true);
            for (var info : entry.getValue()) {
                packet.writeUTF(info[1], true);
                packet.writeUTF(info[0], true);
            }
        }

        packet.writeByte(this.whisperMessages.size());
        for(var entry : this.whisperMessages.entrySet()) {
            packet.writeUTF(entry.getKey(), true);
            packet.writeByte(entry.getValue().size());
            for(var info : entry.getValue()) {
                packet.writeUTF(info[1], true);
                packet.writeUTF(info[0], true);
            }
        }

        return packet;
    }
}