package org.transformice.packets.send.room;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.RoomPlayerInfo;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendRoomPlayerList extends PSSendPacket {
    private final List<RoomPlayerInfo> playerInfoList;

    /**
     * Creates a new packet of SEND_ROOM_PLAYERLIST.
     * @param playerInfoList The information about every player in the room.
     */
    public PacketSendRoomPlayerList(List<RoomPlayerInfo> playerInfoList) {
        super(PacketOpcodes.SEND_ROOM_PLAYERLIST);
        this.playerInfoList = playerInfoList;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUnsignedShort(this.playerInfoList.size());
        for (RoomPlayerInfo info : this.playerInfoList) {
            packet.writeUTF(info.playerName, true);
            packet.writeInt(info.sessionId);
            packet.writeBoolean(info.isShaman);
            packet.writeByte(info.playerState.ordinal());
            packet.writeShort(info.playerScore);
            packet.writeByte(info.cheeseCount);
            packet.writeShort(info.titleInfo.titleId);
            packet.writeByte(info.titleInfo.titleStars);
            packet.writeByte(info.playerGender.ordinal());
            packet.writeUTF(info.playerAvatarId, true);
            packet.writeUTF(info.playerMouseLook, true);
            packet.writeBoolean(info.unk1);
            packet.writeInt(info.playerMouseColor);
            packet.writeInt(info.playerShamanColor);
            packet.writeInt(info.playerStaffNickNameColor);
            packet.writeInt(info.playerNickNameColor);
            packet.writeUnsignedByte(info.playerContextId);
        }

        return packet;
    }
}