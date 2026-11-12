package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaStopMusic extends PSSendPacket {
    private final String channel;
    private final boolean disable;

    /**
     * Creates a new packet of SEND_LUA_STOP_MUSIC.
     * @param channel The music's channel.
     * @param disable Should disable it.
     */
    public PacketSendLuaStopMusic(String channel, boolean disable) {
        super(PacketOpcodes.SEND_LUA_STOP_MUSIC);
        this.channel = channel;
        this.disable = disable;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.channel, true);
        packet.writeBoolean(this.disable);
        return packet;
    }
}