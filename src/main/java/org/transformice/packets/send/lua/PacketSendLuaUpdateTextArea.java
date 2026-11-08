package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaUpdateTextArea extends PSSendPacket {
    private final int textAreaId;
    private final String text;

    /**
     * Creates a new packet of SEND_LUA_UPDATE_TEXT_AREA.
     * @param textAreaId The text area's id.
     * @param text The text area's text.
     */
    public PacketSendLuaUpdateTextArea(int textAreaId, String text) {
        super(PacketOpcodes.SEND_LUA_UPDATE_TEXT_AREA);
        this.textAreaId = textAreaId;
        this.text = text;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.textAreaId);
        packet.writeUTF(this.text, true);
        return packet;
    }
}