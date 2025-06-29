package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddBonus implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddBonus(int type, int x, int y, int angle, int bonus_id, boolean visible) {
        this.byteArray.writeInt128(x);
        this.byteArray.writeInt128(y);
        this.byteArray.writeInt128(type);
        this.byteArray.writeInt128(angle);
        this.byteArray.writeInt128(bonus_id);
        this.byteArray.writeByte((byte) (visible ? 1 : 0));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}