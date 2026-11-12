package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaNicknameColor extends PSSendPacket {
    private final int sessionId;
    private final int color;

    /**
     * Creates a new packet of SEND_LUA_NICKNAME_COLOR.
     * @param sessionId The player's session id.
     * @param color The player's nick name color to set.
     */
    public PacketSendLuaNicknameColor(int sessionId, int color) {
        super(PacketOpcodes.SEND_LUA_NICKNAME_COLOR);
        this.sessionId = sessionId;
        this.color = color;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.sessionId).writeInt(this.color);
    }
}