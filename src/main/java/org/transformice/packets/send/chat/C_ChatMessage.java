package org.transformice.packets.send.chat;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ChatMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ChatMessage(String playerName, String rawMessage) {
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(rawMessage);
        this.byteArray.writeBoolean(false);
    }

    @Override
    public int getC() {
        return 6;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}