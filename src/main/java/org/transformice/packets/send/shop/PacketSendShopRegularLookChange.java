package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopRegularLookChange extends PSSendPacket {
    private final String playerLook;
    private final int mouseColor;

    /**
     * Creates a new packet of SEND_SHOP_REGULAR_LOOK_CHANGE.
     * @param playerLook The player's look.
     * @param mouseColor The player's mouse color.
     */
    public PacketSendShopRegularLookChange(String playerLook, int mouseColor) {
        super(PacketOpcodes.SEND_SHOP_REGULAR_LOOK_CHANGE);
        this.playerLook = playerLook;
        this.mouseColor = mouseColor;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        PSByteArray packet = new PSByteArray();
        String[] look = this.playerLook.split(";");
        packet.writeUnsignedShort(Integer.parseInt(look[0]));
        for (String item : look[1].split(",")) {
            if (item.contains("_")) {
                String[] parts = item.split("_");
                String custom = (parts.length >= 2) ? parts[1] : "";
                String[] realCustom = custom.isEmpty() ? new String[0] : custom.split("\\+");
                packet.writeInt(Integer.parseInt(parts[0]));
                packet.writeByte(realCustom.length);
                for (String customPart : realCustom) {
                    packet.writeInt(Integer.parseInt(customPart, 16));
                }
            } else {
                packet.writeInt(Integer.parseInt(item));
                packet.writeByte(0);
            }
        }

        packet.writeInt(this.mouseColor);
        return packet;
    }
}