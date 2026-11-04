package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerShowEmoji extends PSSendPacket {
    private final int sessionId;
    private final int emojiId;

    /**
     * Creates a new packet of SEND_PLAYER_SHOW_EMOJI.
     * @param sessionId The player's session id.
     * @param emojiId The emoji id.
     */
    public PacketSendPlayerShowEmoji(int sessionId, int emojiId) {
        super(PacketOpcodes.SEND_PLAYER_SHOW_EMOJI);
        this.sessionId = sessionId;
        this.emojiId = emojiId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeUnsignedShort(this.emojiId);
    }
}