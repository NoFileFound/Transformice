package org.transformice.packets.send.player;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerAvailableEmoji extends PSSendPacket {
    private final List<Integer> emojiList;

    /**
     * Sends a new packet of SEND_PLAYER_AVAILABLE_EMOJI.
     * @param emojiList The list of emoji.
     */
    public PacketSendPlayerAvailableEmoji(List<Integer> emojiList) {
        super(PacketOpcodes.SEND_PLAYER_AVAILABLE_EMOJI);
        this.emojiList = emojiList;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt128(this.emojiList.size());
        for (Integer emoji : this.emojiList)
            packet.writeInt128(emoji);

        return packet;
    }
}