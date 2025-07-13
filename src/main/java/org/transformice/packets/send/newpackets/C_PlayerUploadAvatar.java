package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

/**
 * 💀
 */
public final class C_PlayerUploadAvatar implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerUploadAvatar() {
        this.byteArray.writeString(String.format("%02x", System.currentTimeMillis()));
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 51;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}