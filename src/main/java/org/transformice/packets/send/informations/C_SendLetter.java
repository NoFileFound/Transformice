package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SendLetter implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SendLetter(String playerName, String playerLook, byte letterType, byte[] content) {
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(playerLook);
        this.byteArray.writeByte(letterType);
        this.byteArray.writeBytes(content);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}