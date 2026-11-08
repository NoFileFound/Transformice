package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaRemovePhysicObject extends PSSendPacket {
    private final int objectId;

    /**
     * Creates a new packet of SEND_LUA_REMOVE_PHYSIC_OBJECT.
     * @param objectId The object's id.
     */
    public PacketSendLuaRemovePhysicObject(int objectId) {
        super(PacketOpcodes.SEND_LUA_REMOVE_PHYSIC_OBJECT);
        this.objectId = objectId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new NetByteArray().writeInt128(this.objectId);
    }
}