package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaRemoveImage extends PSSendPacket {
    private final int imageId;
    private final boolean fadeOut;

    /**
     * Creates a new packet of SEND_LUA_REMOVE_IMAGE.
     * @param imageId The image's id.
     * @param fadeOut The image's fade out.
     */
    public PacketSendLuaRemoveImage(int imageId, boolean fadeOut) {
        super(PacketOpcodes.SEND_LUA_REMOVE_IMAGE);
        this.imageId = imageId;
        this.fadeOut = fadeOut;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.imageId).writeBoolean(this.fadeOut);
    }
}