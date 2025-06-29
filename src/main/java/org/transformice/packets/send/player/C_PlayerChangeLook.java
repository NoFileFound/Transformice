package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerChangeLook implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerChangeLook(int sessionId, String playerLook) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeString(playerLook);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 36;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}