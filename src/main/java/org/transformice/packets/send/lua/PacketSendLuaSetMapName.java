package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaSetMapName extends PSSendPacket {
    private final String mapName;

    /**
     * Creates a new packet of SEND_LUA_SET_MAP_NAME.
     * @param mapName The map's name.
     */
    public PacketSendLuaSetMapName(String mapName) {
        super(PacketOpcodes.SEND_LUA_SET_MAP_NAME);
        this.mapName = mapName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeUTF(this.mapName, true);
    }
}