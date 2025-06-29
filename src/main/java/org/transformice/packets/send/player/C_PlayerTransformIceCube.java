package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerTransformIceCube implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerTransformIceCube(int sessionId, int actionType, int seconds) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(actionType);
        this.byteArray.writeShort((short)seconds);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 45;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}