package org.transformice.packets.send.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MoveObject implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MoveObject(boolean isPhysic, int objectId, int xPosition, int yPosition, boolean positionOffset, int xSpeed, int ySpeed, boolean speedOffset, int angle, boolean angleOffset) {
        this.byteArray.writeBoolean(isPhysic);
        this.byteArray.writeInt128(objectId);
        this.byteArray.writeInt128(xPosition);
        this.byteArray.writeInt128(yPosition);
        this.byteArray.writeBoolean(positionOffset);
        this.byteArray.writeInt128(xSpeed);
        this.byteArray.writeInt128(ySpeed);
        this.byteArray.writeBoolean(speedOffset);
        this.byteArray.writeInt128(angle);
        this.byteArray.writeBoolean(angleOffset);
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}