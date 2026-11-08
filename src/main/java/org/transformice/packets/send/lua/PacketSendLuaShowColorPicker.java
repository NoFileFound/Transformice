package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaShowColorPicker extends PSSendPacket {
    private final int id;
    private final int defaultColor;
    private final String interfaceName;

    /**
     * Creates a new packet of SEND_LUA_SHOW_COLOR_PICKER.
     * @param id The color picker's id.
     * @param defaultColor The color picker's default color.
     * @param interfaceName The interface's name.
     */
    public PacketSendLuaShowColorPicker(int id, int defaultColor, String interfaceName) {
        super(PacketOpcodes.SEND_LUA_SHOW_COLOR_PICKER);
        this.id = id;
        this.defaultColor = defaultColor;
        this.interfaceName = interfaceName;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.id);
        packet.writeInt(this.defaultColor);
        packet.writeUTF(this.interfaceName, true);
        return packet;
    }
}