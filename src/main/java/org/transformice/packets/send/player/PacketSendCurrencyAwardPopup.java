package org.transformice.packets.send.player;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendCurrencyAwardPopup extends PSSendPacket {
    private final int currency;
    private final int amount;

    /**
     * Creates a new packet of SEND_CURRENCY_AWARD_POPUP.
     * @param currency The currency id.
     * @param amount The currency amount.
     */
    public PacketSendCurrencyAwardPopup(int currency, int amount) {
        super(PacketOpcodes.SEND_CURRENCY_AWARD_POPUP);
        this.currency = currency;
        this.amount = amount;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.currency).writeByte(this.amount);
    }
}