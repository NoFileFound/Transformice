package org.transformice.packets.send.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AddImage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AddImage(int imageId, String imageName, int imageTargetId, int targetSessionId, int x, int y, float scaleX, float scaleY, float rotation, float alpha, float anchorX, float anchorY, boolean fadeIn) {
        this.byteArray.writeInt(imageId);
        this.byteArray.writeString(imageName);
        this.byteArray.writeByte(imageTargetId);
        this.byteArray.writeInt(targetSessionId);
        this.byteArray.writeInt(x);
        this.byteArray.writeInt(y);
        this.byteArray.writeFloat(scaleX);
        this.byteArray.writeFloat(scaleY);
        this.byteArray.writeFloat(rotation);
        this.byteArray.writeFloat(alpha);
        this.byteArray.writeFloat(anchorX);
        this.byteArray.writeFloat(anchorY);
        this.byteArray.writeBoolean(fadeIn);
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 19;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}