package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MovePlayer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MovePlayer(int posX, int posY, boolean posRel, int velX, int velY, boolean velRel) {
        this.byteArray.writeInt128(posX);
        this.byteArray.writeInt128(posY);
        this.byteArray.writeBoolean(posRel);
        this.byteArray.writeInt128(velX);
        this.byteArray.writeInt128(velY);
        this.byteArray.writeBoolean(velRel);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}