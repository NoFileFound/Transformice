package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaPlaySound extends PSSendPacket {
    private final String fileName;
    private final int volume;
    private final int posX;
    private final int posY;

    /**
     * Creates a new packet of SEND_LUA_PLAY_SOUND.
     * @param fileName The sound's file name.
     * @param volume The volume.
     * @param posX The X position.
     * @param posY The Y position.
     */
    public PacketSendLuaPlaySound(String fileName, int volume, int posX, int posY) {
        super(PacketOpcodes.SEND_LUA_PLAY_SOUND);
        this.fileName = fileName;
        this.volume = volume;
        this.posX = posX;
        this.posY = posY;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.fileName, true);
        packet.writeShort((short)this.volume);
        packet.writeBoolean(true);
        packet.writeShort((short)this.posX);
        packet.writeShort((short)this.posY);
        return packet;
    }
}