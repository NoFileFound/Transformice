package org.transformice.packets.send.chat;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_HtmlMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_HtmlMessage(String message) {
        this.byteArray.writeString(message);
    }

    @Override
    public int getC() {
        return 6;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}