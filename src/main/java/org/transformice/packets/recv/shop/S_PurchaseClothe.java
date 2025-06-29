package org.transformice.packets.recv.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_PurchaseClothe implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseShopInstance().buyShopClothe(data.readByte(), data.readBoolean());
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 22;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}