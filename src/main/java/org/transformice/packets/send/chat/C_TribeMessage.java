package org.transformice.packets.send.chat;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_TribeMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_TribeMessage(String message, String username) {
        this.byteArray.writeString(message);
        this.byteArray.writeString(username);
    }

    @Override
    public int getC() {
        return 6;
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