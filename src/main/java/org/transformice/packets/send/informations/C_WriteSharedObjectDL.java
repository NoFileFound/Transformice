package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

/**
 * 💀💀💀💀💀💀💀
 */
public final class C_WriteSharedObjectDL implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_WriteSharedObjectDL(String data) {
        this.byteArray.writeString(data);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 65;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}