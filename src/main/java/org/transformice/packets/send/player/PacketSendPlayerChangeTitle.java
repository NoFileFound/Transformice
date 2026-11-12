package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.PlayerGender;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendPlayerChangeTitle extends PSSendPacket {
    private final PlayerGender genderType;
    private final int titleId;

    /**
     * Creates a new packet of SEND_PLAYER_CHANGE_TITLE.
     * @param genderType The player's gender type.
     * @param titleId The player's title to change.
     */
    public PacketSendPlayerChangeTitle(PlayerGender genderType, int titleId) {
        super(PacketOpcodes.SEND_PLAYER_CHANGE_TITLE);
        this.genderType = genderType;
        this.titleId = titleId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedByte(this.genderType.ordinal()).writeUnsignedShort(this.titleId);
    }
}