package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

/**
 * 💀💀💀💀💀💀💀💀💀💀💀💀💀💀💀💀
 */
public final class C_WindSeedClientNotify implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_WindSeedClientNotify(byte[] data) {
        this.byteArray.writeBytes(data);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}