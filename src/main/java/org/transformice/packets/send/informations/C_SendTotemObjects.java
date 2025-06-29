package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SendTotemObjects implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SendTotemObjects(int usedObjects) {
        this.byteArray.writeShort((short)(usedObjects / 2));
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 11;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}