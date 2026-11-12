package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendDestructLuaRoom extends PSSendPacket {
    /**
     * Creates a new packet of SEND_DESTRUCT_LUA_ROOM.
     */
    public PacketSendDestructLuaRoom() {
        super(PacketOpcodes.SEND_DESTRUCT_LUA_ROOM);

    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray();
    }
}