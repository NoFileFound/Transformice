package org.transformice.packets.send.lua;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendLuaAddPopup extends PSSendPacket {
    private final int popupId;
    private final int popupType;
    private final String popupText;
    private final int posX;
    private final int posY;
    private final int width;
    private final boolean fixedPos;

    /**
     * Creates a new packet of SEND_LUA_ADD_POPUP.
     * @param popupId The popup's id.
     * @param popupType The popup's type.
     * @param popupText The popup's text.
     * @param posX The X position.
     * @param posY The Y position.
     * @param width The popup width.
     * @param fixedPos Is the popup in a fixed position.
     */
    public PacketSendLuaAddPopup(int popupId, int popupType, String popupText, int posX, int posY, int width, boolean fixedPos) {
        super(PacketOpcodes.SEND_LUA_ADD_POPUP);
        this.popupId = popupId;
        this.popupType = popupType;
        this.popupText = popupText;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.fixedPos = fixedPos;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.popupId);
        packet.writeByte(this.popupType);
        packet.writeUTF(this.popupText, true);
        packet.writeInt128(this.posX);
        packet.writeInt128(this.posY);
        packet.writeInt128(this.width);
        packet.writeBoolean(this.fixedPos);
        return packet;
    }
}