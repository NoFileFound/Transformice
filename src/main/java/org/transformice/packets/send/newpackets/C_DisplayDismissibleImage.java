package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DisplayDismissibleImage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DisplayDismissibleImage(String imagineId) {
        this.byteArray.writeString(imagineId);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 37;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}