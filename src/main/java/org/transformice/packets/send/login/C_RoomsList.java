package org.transformice.packets.send.login;

// Imports
import static org.transformice.utils.Utils.getCommunityFromLanguage;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.Room;
import org.transformice.Server;
import org.transformice.packets.SendPacket;

public final class C_RoomsList implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RoomsList(Client player, int type) {
        player.currentGameMode = type = type == 0 ? 1 : type;
        this.byteArray.writeByte(8);
        for (byte roomType : new byte[]{1, 3, 8, 9, 2, 10, 18, 16}) {
            this.byteArray.writeByte(roomType);
        }

        this.byteArray.writeByte(type);
        if(type == 18) {
            this.byteArray.writeByte(1).writeString("int").writeString("int").writeString("v Modules").writeString("0").writeString("lm").writeString(convertMinigamesToStr(player.getServer()));
        }

        int cnt = 0;
        for(Room roomInfo : player.getServer().getRoomsByGameMode(type, player.playerCommunity)) {
            if(roomInfo.getRoomName().substring(roomInfo.getRoomName().indexOf('-') + 1).startsWith("@")) continue; // private room

            Room.RoomDetails roomDetails = roomInfo.getRoomDetails();
            this.byteArray.writeByte(0); // normal room
            this.byteArray.writeString(roomInfo.getRoomCommunity());
            this.byteArray.writeString(roomInfo.getRoomName().startsWith("*") ? "int" : getCommunityFromLanguage(player.playerCommunity));
            if(roomInfo.getRoomName().startsWith("*")) {
                this.byteArray.writeString(roomInfo.getRoomName());
            } else {
                this.byteArray.writeString(roomInfo.getRoomName().substring(roomInfo.getRoomName().indexOf('-') + 1));
            }
            this.byteArray.writeUnsignedShort(roomInfo.getPlayersCount());
            this.byteArray.writeUnsignedByte(roomInfo.getMaximumPlayers());
            this.byteArray.writeBoolean(roomInfo.isFunCorpHighlighedRoom);
            this.byteArray.writeBoolean(roomDetails != null && this.checkShowRoomPopup(roomDetails));
            if(roomDetails != null && this.checkShowRoomPopup(roomDetails)) {
                this.byteArray.writeBoolean(roomDetails.withoutShamanSkills);
                this.byteArray.writeBoolean(roomDetails.withoutPhysicalConsumables);
                this.byteArray.writeBoolean(roomDetails.withoutAdventureMaps);
                this.byteArray.writeBoolean(roomDetails.withMiceCollisions);
                this.byteArray.writeBoolean(roomDetails.withFallDamage);
                this.byteArray.writeUnsignedByte(roomDetails.roundDuration);
                this.byteArray.writeInt(roomDetails.miceWeight);
                this.byteArray.writeShort(roomDetails.maximumPlayers);
                this.byteArray.writeByte(roomDetails.mapRotation.size());
                for(int i : roomDetails.mapRotation) {
                    this.byteArray.writeUnsignedByte(i);
                }
            }
            cnt++;
        }

        if(cnt == 0 && type != 18) {
            String roomName = (type == 1 ? "1" : (type == 3) ? "vanilla1" : (type == 8) ? "survivor1" : (type == 9) ? "racing1" : (type == 2) ? "bootcamp1" : (type == 10) ? "defilante1" : (type == 16) ? "village1" : "1");
            this.byteArray.writeByte(0);
            this.byteArray.writeString(player.playerCommunity);
            this.byteArray.writeString(getCommunityFromLanguage(player.playerCommunity));
            this.byteArray.writeString(roomName);
            this.byteArray.writeUnsignedShort(0);
            this.byteArray.writeUnsignedByte(255);
            this.byteArray.writeBoolean(false);
            this.byteArray.writeBoolean(false);
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 35;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }

    /**
     * Shows the room details popup in the room list.
     * @param details The room details.
     * @return True if show or else False.
     */
    private boolean checkShowRoomPopup(Room.RoomDetails details) {
        if(details.withoutShamanSkills) return true;
        if(details.withoutPhysicalConsumables) return true;
        if(details.withoutAdventureMaps) return true;
        if(details.withMiceCollisions) return true;
        if(details.withFallDamage) return true;
        if(details.roundDuration != 100 && details.roundDuration != 0) return true;
        if(details.miceWeight != 0) return true;
        if(details.maximumPlayers != 0) return true;
        return !details.mapRotation.isEmpty();
    }

    /**
     * Convert minigames to string for modules.
     * @param server The server.
     * @return A string contains every minigame.
     */
    private static String convertMinigamesToStr(Server server) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < server.getMinigameList().size(); i++) {
            sb.append("&~#").append(server.getMinigameList().get(i)).append(",0");
        }
        return sb.toString();
    }
}