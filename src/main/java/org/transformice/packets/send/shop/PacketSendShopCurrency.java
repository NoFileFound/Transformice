package org.transformice.packets.send.shop;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendShopCurrency extends PSSendPacket {
    private final int cheeseCount;
    private final int strawberryCount;

    /**
     * Creates a new packet of SEND_SHOP_CURRENCY.
     * @param cheeseCount The player's cheese count.
     * @param strawberryCount The player's strawberry count.
     */
    public PacketSendShopCurrency(int cheeseCount, int strawberryCount) {
        super(PacketOpcodes.SEND_SHOP_CURRENCY);
        this.cheeseCount = cheeseCount;
        this.strawberryCount = strawberryCount;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeInt(this.cheeseCount).writeInt(this.strawberryCount);
    }
}