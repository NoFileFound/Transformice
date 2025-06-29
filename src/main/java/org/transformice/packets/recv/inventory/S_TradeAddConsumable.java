package org.transformice.packets.recv.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_TradeAddConsumable implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseInventoryInstance().addTradeConsumable(data.readShort(), data.readBoolean());
    }

    @Override
    public int getC() {
        return 31;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}