package org.transformice.packets.recv.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_GiftItem implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseShopInstance().sendShopGift(data.readString(), data.readBoolean(), data.readInt(), data.readString());
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 28;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}