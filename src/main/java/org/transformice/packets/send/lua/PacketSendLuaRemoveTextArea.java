package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaRemoveTextArea extends PSSendPacket {
    private final int textAreaId;

    /**
     * Creates a new packet of SEND_LUA_REMOVE_TEXT_AREA.
     * @param textAreaId The text area's id.
     */
    public PacketSendLuaRemoveTextArea(int textAreaId) {
        super(PacketOpcodes.SEND_LUA_REMOVE_TEXT_AREA);
        this.textAreaId = textAreaId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.textAreaId);
    }
}