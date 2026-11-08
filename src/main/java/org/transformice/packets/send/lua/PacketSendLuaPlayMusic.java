package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaPlayMusic extends PSSendPacket {
    private final String fileName;
    private final String channel;
    private final int volume;
    private final boolean loop;
    private final boolean fade;

    /**
     * Creates a new packet of SEND_LUA_PLAY_MUSIC.
     * @param fileName The music's file name.
     * @param channel The channel.
     * @param volume The volume.
     * @param fade Music fade.
     * @param loop Should repeat?
     */
    public PacketSendLuaPlayMusic(String fileName, String channel, int volume, boolean loop, boolean fade) {
        super(PacketOpcodes.SEND_LUA_PLAY_MUSIC);
        this.fileName = fileName;
        this.channel = channel;
        this.volume = volume;
        this.loop = loop;
        this.fade = fade;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.fileName, true);
        packet.writeUTF(this.channel, true);
        packet.writeShort((short)this.volume);
        packet.writeBoolean(this.loop);
        packet.writeBoolean(this.fade);
        return packet;
    }
}