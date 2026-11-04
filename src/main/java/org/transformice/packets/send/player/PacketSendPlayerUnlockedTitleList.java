package org.transformice.packets.send.player;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.PlayerTitleInfo;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerUnlockedTitleList extends PSSendPacket {
    private final List<Short> unlockedTitlesWithoutStars;
    private final List<PlayerTitleInfo> unlockedTitlesWithStars;

    /**
     * Creates a new packet of SEND_PLAYER_UNLOCKED_TITLE_LIST.
     * @param unlockedTitles The player's unlocked title list.
     */
    public PacketSendPlayerUnlockedTitleList(List<PlayerTitleInfo> unlockedTitles) {
        super(PacketOpcodes.SEND_PLAYER_UNLOCKED_TITLE_LIST);
        this.unlockedTitlesWithoutStars = new ArrayList<>();
        this.unlockedTitlesWithStars = new ArrayList<>();
        for (PlayerTitleInfo titleInfo : unlockedTitles) {
            if (titleInfo.titleStars == 0)
                this.unlockedTitlesWithoutStars.add(titleInfo.titleId);
            else
                this.unlockedTitlesWithStars.add(titleInfo);
        }
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeShort((short) this.unlockedTitlesWithoutStars.size());
        for (short titleId : this.unlockedTitlesWithoutStars)
            packet.writeShort(titleId);

        packet.writeShort((short) this.unlockedTitlesWithStars.size());
        for (PlayerTitleInfo titleInfo : this.unlockedTitlesWithStars) {
            packet.writeShort(titleInfo.titleId);
            packet.writeByte(titleInfo.titleStars);
        }

        return packet;
    }
}