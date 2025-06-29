package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CollectibleActionPacket implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CollectibleActionPacket(int sessionId) {
        this.byteArray.writeUnsignedByte(3);
        this.byteArray.writeInt(sessionId);
    }

    public C_CollectibleActionPacket(int type, boolean can, int sessionId, String imgPath, int offsetX, int offsetY, boolean foreground, int sizePer, int angle) {
        this.byteArray.writeUnsignedByte(type);
        if(type == 1) {
            this.byteArray.writeBoolean(can);
        } else {
            this.byteArray.writeInt(sessionId);
            this.byteArray.writeString(imgPath);
            this.byteArray.writeShort((short)offsetX);
            this.byteArray.writeShort((short)offsetY);
            this.byteArray.writeBoolean(foreground);
            this.byteArray.writeShort((short)sizePer);
            this.byteArray.writeShort((short)angle);
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 101;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}