package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendSaveWallpaper extends PSSendPacket {
    private final String fileId;

    /**
     * Creates a new packet of SEND_SAVE_WALLPAPER.
     * @param fileId The file's id.
     */
    public PacketSendSaveWallpaper(String fileId) {
        super(PacketOpcodes.SEND_SAVE_WALLPAPER);
        this.fileId = fileId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.fileId, true);
    }
}