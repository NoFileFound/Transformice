package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.RoomPlayerInfo;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomNewPlayer extends PSSendPacket {
    private final RoomPlayerInfo playerInfo;
    private final boolean skipAnim;
    private final boolean isVisible;

    /**
     * Creates a new packet of SEND_ROOM_NEW_PLAYER.
     * @param playerInfo The player info.
     * @param skipAnim Should skip the animation.
     * @param isHidden Is the player hidden.
     */
    public PacketSendRoomNewPlayer(RoomPlayerInfo playerInfo, boolean skipAnim, boolean isVisible) {
        super(PacketOpcodes.SEND_ROOM_NEW_PLAYER);
        this.playerInfo = playerInfo;
        this.skipAnim = skipAnim;
        this.isVisible = isVisible;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerInfo.playerName, true);
        packet.writeInt(this.playerInfo.sessionId);
        packet.writeBoolean(this.playerInfo.isShaman);
        packet.writeByte(this.playerInfo.playerState.ordinal());
        packet.writeShort(this.playerInfo.playerScore);
        packet.writeByte(this.playerInfo.cheeseCount);
        packet.writeShort(this.playerInfo.titleInfo.titleId);
        packet.writeByte(this.playerInfo.titleInfo.titleStars);
        packet.writeByte(this.playerInfo.playerGender.ordinal());
        packet.writeUTF(this.playerInfo.playerAvatarId, true);
        packet.writeUTF(this.playerInfo.playerMouseLook, true);
        packet.writeBoolean(this.playerInfo.unk1);
        packet.writeInt(this.playerInfo.playerMouseColor);
        packet.writeInt(this.playerInfo.playerShamanColor);
        packet.writeInt(this.playerInfo.playerStaffNickNameColor);
        packet.writeInt(this.playerInfo.playerNickNameColor);
        packet.writeUnsignedByte(this.playerInfo.playerContextId);
        packet.writeBoolean(this.skipAnim);
        packet.writeBoolean(this.isVisible);
        return packet;
    }
}