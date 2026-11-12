package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaRemoveJoint extends PSSendPacket {
    private final int jointId;

    /**
     * Creates a new packet of SEND_LUA_REMOVE_JOINT.
     * @param jointId The joint's id.
     */
    public PacketSendLuaRemoveJoint(int jointId) {
        super(PacketOpcodes.SEND_LUA_REMOVE_JOINT);
        this.jointId = jointId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.jointId);
    }
}