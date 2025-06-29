package org.transformice.packets.send.modopwet;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ModopwetRoomPasswordMsg implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ModopwetRoomPasswordMsg(String playerName, String roomName, boolean isPasswordProtected) {
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(roomName);
        this.byteArray.writeBoolean(isPasswordProtected);
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 4;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}