package org.transformice.packets.recv.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_PurchaseItem implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseShopInstance().buyShopItem(data.readInt(), data.readBoolean(), data.readShort());
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 19;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}