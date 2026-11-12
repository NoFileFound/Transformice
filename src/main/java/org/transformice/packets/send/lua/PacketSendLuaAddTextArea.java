package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaAddTextArea extends PSSendPacket {
    private final int txtAreaId;
    private final String text;
    private final int posX;
    private final int posY;
    private final int width;
    private final int height;
    private final int backgroundColor;
    private final int borderColor;
    private final int backgroundAlpha;
    private final boolean fixedPos;

    /**
     * Creates a new packet of SEND_LUA_ADD_TEXT_AREA.
     * @param txtAreaId The text area's id.
     * @param text The text area's text.
     * @param posX The text area's position X.
     * @param posY The text area's position Y.
     * @param width The text area's width.
     * @param height The text area's height.
     * @param backgroundColor The text area's background color.
     * @param borderColor The text area's border color.
     * @param backgroundAlpha The text area's alpha background.
     * @param fixedPos The text area's fixed position.
     */
    public PacketSendLuaAddTextArea(int txtAreaId, String text, int posX, int posY, int width, int height, int backgroundColor, int borderColor, int backgroundAlpha, boolean fixedPos) {
        super(PacketOpcodes.SEND_LUA_ADD_TEXT_AREA);
        this.txtAreaId = txtAreaId;
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.backgroundAlpha = backgroundAlpha;
        this.fixedPos = fixedPos;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.txtAreaId);
        packet.writeUTF(this.text, true);
        packet.writeInt128(this.posX);
        packet.writeInt128(this.posY);
        packet.writeInt128(this.width);
        packet.writeInt128(this.height);
        packet.writeInt(this.backgroundColor);
        packet.writeInt(this.borderColor);
        packet.writeByte(this.backgroundAlpha);
        packet.writeBoolean(this.fixedPos);
        return packet;
    }
}