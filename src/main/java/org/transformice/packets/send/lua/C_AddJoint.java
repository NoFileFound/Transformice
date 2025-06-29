package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddJoint implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddJoint(ByteArray byteArray) {
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeByte(byteArray.readByte());
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeShort((short)(byteArray.readShort() * 100));
        this.byteArray.writeShort((short)(byteArray.readShort() * 100));
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeInt(byteArray.readInt());
        this.byteArray.writeShort((short)(byteArray.readShort() * 100));
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort(byteArray.readShort());
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort((short)(byteArray.readShort() * 100));
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort((short)(byteArray.readShort() * 100));
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort((short)(byteArray.readShort() * 100));
        this.byteArray.writeBoolean(byteArray.readBoolean());
        this.byteArray.writeShort((short)(byteArray.readShort() * 100));
        this.byteArray.writeShort((short)(byteArray.readShort() * 100));
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}