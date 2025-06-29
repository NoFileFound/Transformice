package org.transformice.packets.send.room;

// Imports
import static org.transformice.utils.Utils.getCommunityFromLanguage;
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_EnterRoom implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_EnterRoom(String roomName) {
        this.byteArray.writeBoolean(this.checkRoom(roomName));
        this.byteArray.writeString(roomName);
        this.byteArray.writeString(roomName.startsWith("*") ? "int" : getCommunityFromLanguage(roomName.substring(0, roomName.indexOf("-"))));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }

    /**
     * Checks if the current room is official.
     * @param roomName The room name.
     * @return True if its official or else false.
     */
    private boolean checkRoom(String roomName) {
        if(roomName.startsWith("*") && roomName.contains(String.valueOf((char)3))) return false;
        if(roomName.startsWith("*")) {
            roomName = roomName.substring(1);
        } else {
            roomName = roomName.substring(roomName.indexOf("-") + 1);
        }

        boolean found = false;
        String count = roomName.replaceAll("\\D", "");
        List<String> roomTypes = List.of("vanilla", "survivor", "racing", "music", "bootcamp", "defilante", "village");
        for (String room : roomTypes) {
            if ((roomName.startsWith(room) && !count.isEmpty()) || roomName.matches("\\d+")) {
                int countValue = count.isEmpty() ? 0 : Integer.parseInt(count);
                found = !(countValue < 1 || countValue > 1_000_000_000 || roomName.equals(room));
            }
        }

        return found;
    }
}