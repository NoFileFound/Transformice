package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MiniLogMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MiniLogMessage(int x, int y, String title, String message) {
        this.byteArray.writeShort((short)(x * y));
        this.byteArray.writeString(title);
        this.byteArray.writeString(message);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 17;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}