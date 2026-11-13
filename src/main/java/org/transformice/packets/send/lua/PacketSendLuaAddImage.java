package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaAddImage extends PSSendPacket {
    private final int imageId;
    private final String imageName;
    private final int imageTargetId;
    private final int targetSessionId;
    private final int x;
    private final int y;
    private final float scaleX;
    private final float scaleY;
    private final float rotation;
    private final float alpha;
    private final float anchorX;
    private final float anchorY;
    private final boolean fadeIn;

    /**
     * Creates a new packet of SEND_LUA_ADD_IMAGE.
     * @param imageId The image's id.
     * @param imageName The image's name.
     * @param imageTargetId The image's target id.
     * @param targetSessionId The target's session id.
     * @param x The X position.
     * @param y The Y position.
     * @param scaleX The scale X.
     * @param scaleY The scale Y.
     * @param rotation The rotation.
     * @param alpha The alpha.
     * @param anchorX The anchor X.
     * @param anchorY The anchor Y.
     * @param fadeIn The fade in.
     */
    public PacketSendLuaAddImage(int imageId, String imageName, int imageTargetId, int targetSessionId, int x, int y, float scaleX, float scaleY, float rotation, float alpha, float anchorX, float anchorY, boolean fadeIn) {
        super(PacketOpcodes.SEND_LUA_ADD_IMAGE);
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageTargetId = imageTargetId;
        this.targetSessionId = targetSessionId;
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
        this.alpha = alpha;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.fadeIn = fadeIn;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.imageId);
        packet.writeUTF(this.imageName, true);
        packet.writeByte(this.imageTargetId);
        packet.writeInt(this.targetSessionId);
        packet.writeInt(this.x);
        packet.writeInt(this.y);
        packet.writeFloat(this.scaleX);
        packet.writeFloat(this.scaleY);
        packet.writeFloat(this.rotation);
        packet.writeFloat(this.alpha);
        packet.writeFloat(this.anchorX);
        packet.writeFloat(this.anchorY);
        packet.writeBoolean(this.fadeIn);
        return packet;
    }
}