package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaBindKeyboard extends PSSendPacket {
    private final int keyCode;
    private final boolean isReleased;
    private final boolean isEnabled;

    /**
     * Creates a new packet of SEND_LUA_BIND_KEYBOARD.
     * @param keyCode The key's code.
     * @param isReleased Is the key released.
     * @param isEnabled is the bind enabled.
     */
    public PacketSendLuaBindKeyboard(int keyCode, boolean isReleased, boolean isEnabled) {
        super(PacketOpcodes.SEND_LUA_BIND_KEYBOARD);
        this.keyCode = keyCode;
        this.isReleased = isReleased;
        this.isEnabled = isEnabled;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeShort((short) this.keyCode).writeBoolean(this.isReleased).writeBoolean(this.isEnabled);
    }
}