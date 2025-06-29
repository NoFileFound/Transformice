package org.transformice.packets.send.transformation;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_Transformation implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_Transformation(int sessionId, short transformType) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeShort(transformType);
    }

    @Override
    public int getC() {
        return 27;
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