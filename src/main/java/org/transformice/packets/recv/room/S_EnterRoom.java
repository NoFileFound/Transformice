package org.transformice.packets.recv.room;

// Imports
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.Room;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_EnterRoom implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        String community = data.readString();
        String roomName = data.readString();
        String password = data.readString();
        boolean salonAuto = data.readBoolean();
        if(roomName.isEmpty() || salonAuto) {
            client.sendEnterRoom(client.getServer().getRecommendedRoom(roomName), "");
            return;
        }

        if((client.getRoomName().matches("^[a-zA-Z]{2}-.*") && client.getRoomName().substring(3).equals(roomName)) || client.getRoomName().equals(roomName) || client.getRoom().isEditeur()) {
            return;
        }

        roomName = Pattern.compile("^([a-z]{2})-").matcher(roomName).replaceFirst(m -> m.group(1).toUpperCase() + '-');
        Room.RoomDetails details = new Room.RoomDetails();
        if(data.getLength() > 0) {
            details.roomPassword = data.readString();
            details.withoutShamanSkills = data.readBoolean();
            details.withoutPhysicalConsumables = data.readBoolean();
            details.withoutAdventureMaps = data.readBoolean();
            details.withMiceCollisions = data.readBoolean();
            details.withFallDamage = data.readBoolean();
            details.roundDuration = data.readUnsignedByte();
            details.miceWeight = data.readInt();
            details.maximumPlayers = data.readShort();
            details.mapRotation = new ArrayList<>();

            int sz = data.readUnsignedByte();
            for(int i = 0; i < sz; i++) {
                details.mapRotation.add((int)data.readByte());
            }
        }

        client.sendEnterRoom(roomName, password, details);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 38;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}