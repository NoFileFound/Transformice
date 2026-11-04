package org.transformice.packets.send.transformice;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendMapEditorExportPrice extends PSSendPacket {
    private final int cheesePrice;

    /**
     * Creates a new packet of SEND_MAPEDITOR_EXPORT_PRICE.
     * @param cheesePrice The cheese price need to export a map from the map editor.
     */
    public PacketSendMapEditorExportPrice(int cheesePrice) {
        super(PacketOpcodes.SEND_MAPEDITOR_EXPORT_PRICE);
        this.cheesePrice = cheesePrice;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeUnsignedShort(this.cheesePrice);
    }
}