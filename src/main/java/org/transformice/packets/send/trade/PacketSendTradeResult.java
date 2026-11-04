package org.transformice.packets.send.trade;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.data.objects.TradeResultType;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTradeResult extends PSSendPacket {
    private final String playerName;
    private final TradeResultType result;

    /**
     * Creates a new packet of SEND_TRADE_RESULT.
     * @param playerName The player's name.
     * @param result The trade's result.
     */
    public PacketSendTradeResult(String playerName, TradeResultType result) {
        super(PacketOpcodes.SEND_TRADE_RESULT);
        this.playerName = playerName;
        this.result = result;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.playerName, true);
        packet.writeByte(this.result.ordinal());
        return packet;
    }
}