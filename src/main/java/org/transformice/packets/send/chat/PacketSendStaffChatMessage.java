package org.transformice.packets.send.chat;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendStaffChatMessage extends PSSendPacket {
    private final int channelId;
    private final String playerName;
    private final String message;
    private final List<String> trArgs;
    private final boolean isTabbed;

    /**
     * Creates a new packet of SEND_STAFF_CHAT_MESSAGE.
     * @param channelId The staff chat's channel id.
     * @param playerName The player's name.
     * @param message The message's content.
     */
    public PacketSendStaffChatMessage(int channelId, String playerName, String message) {
        super(PacketOpcodes.SEND_STAFF_CHAT_MESSAGE);
        this.channelId = channelId;
        this.playerName = playerName;
        this.message = message;
        this.trArgs = List.of();
        this.isTabbed = false;
    }

    /**
     * Creates a new packet of SEND_STAFF_CHAT_MESSAGE.
     * @param channelId The staff chat's channel id.
     * @param playerName The player's name.
     * @param message The message's content.
     * @param trArgs The message's template arguments.
     */
    public PacketSendStaffChatMessage(int channelId, String playerName, String message, List<String> trArgs, boolean isTabbed) {
        super(PacketOpcodes.SEND_STAFF_CHAT_MESSAGE);
        this.channelId = channelId;
        this.playerName = playerName;
        this.message = message;
        this.trArgs = trArgs;
        this.isTabbed = isTabbed;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.channelId);
        packet.writeUTF(this.playerName, true);
        packet.writeUTF(this.message, true);
        packet.writeBoolean(this.isTabbed);
        packet.writeBoolean(!this.trArgs.isEmpty());
        packet.writeUnsignedByte(this.trArgs.size());
        for(String trArg : this.trArgs)
            packet.writeUTF(trArg, true);

        return packet;
    }
}