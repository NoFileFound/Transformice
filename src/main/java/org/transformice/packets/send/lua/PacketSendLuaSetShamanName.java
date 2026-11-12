package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaSetShamanName extends PSSendPacket {
    private final String shamanName;

    /**
     * Creates a new packet of SEND_LUA_SET_SHAMAN_NAME.
     * @param shamanName The shaman's name.
     */
    public PacketSendLuaSetShamanName(String shamanName) {
        super(PacketOpcodes.SEND_LUA_SET_SHAMAN_NAME);
        this.shamanName = shamanName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.shamanName, true);
    }
}