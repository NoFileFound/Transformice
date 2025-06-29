package org.transformice.packets.send.modopwet;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ModopwetUpdateRoomModerators implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ModopwetUpdateRoomModerators(String roomName, List<String> roomMods) {
        this.byteArray.writeString(roomName);
        this.byteArray.writeByte(roomMods.size());
        for(String mod : roomMods) {
            this.byteArray.writeString(mod);
        }
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}