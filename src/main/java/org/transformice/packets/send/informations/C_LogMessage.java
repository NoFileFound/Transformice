package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LogMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LogMessage(int fontType, String message) {
        this.byteArray.writeByte(fontType);
        this.byteArray.writeString("");
        this.byteArray.writeUnsignedByte((message.length() >> 16) & 0xFF);
        this.byteArray.writeUnsignedByte((message.length() >> 8) & 0xFF);
        this.byteArray.writeUnsignedByte((message.length() & 0xFF));
        this.byteArray.writeBytes(message.getBytes());
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 46;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}