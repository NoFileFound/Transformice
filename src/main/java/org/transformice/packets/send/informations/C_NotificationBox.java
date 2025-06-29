package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_NotificationBox implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_NotificationBox(String title, String description) {
        this.byteArray.writeString(title);
        this.byteArray.writeString(description);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 60;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}