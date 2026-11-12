package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShamanTag extends PSSendPacket {
    private final int type;
    private final short x;
    private final short y;

    /**
     * Creates a new packet of SEND_SHAMAN_TAG.
     * @param type The tag's id.
     * @param x The X position.
     * @param y The Y position.
     */
    public PacketSendShamanTag(int type, short x, short y) {
        super(PacketOpcodes.SEND_SHAMAN_TAG);
        this.type = type;
        this.x = x;
        this.y = y;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.type).writeShort(this.x).writeShort(this.y);
    }
}