package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerInvocation implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerInvocation(int sessionId, short objectCode, short posX, short posY, short angle, String rotation, boolean invocation) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeShort(objectCode);
        this.byteArray.writeShort(posX);
        this.byteArray.writeShort(posY);
        this.byteArray.writeShort(angle);
        this.byteArray.writeString(rotation);
        this.byteArray.writeBoolean(invocation);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}