package org.transformice.packets.send.legacy;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_Totem implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_Totem(int sessionId, int x, int y, String totemInfo) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(1);
        this.byteArray.writeInt(x);
        this.byteArray.writeByte(1);
        this.byteArray.writeInt(y);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(totemInfo, false);
    }

    @Override
    public int getC() {
        return 22;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}