package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SpawnObject implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SpawnObject(int objectId, int shamanObjectId, int x, int y, int angle, int velx, int vely, boolean has_contact_listener, boolean is_collidable, byte[] colors) {
        this.byteArray.writeInt(objectId);
        this.byteArray.writeInt128(shamanObjectId);
        this.byteArray.writeInt128(x);
        this.byteArray.writeInt128(y);
        this.byteArray.writeInt128(angle * 100);
        this.byteArray.writeInt128(velx * 100);
        this.byteArray.writeInt128(vely * 100);
        this.byteArray.writeBoolean(has_contact_listener);
        this.byteArray.writeBoolean(is_collidable);
        this.byteArray.writeByte(colors.length);
        for(int color : colors) {
            this.byteArray.writeInt(color);
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}