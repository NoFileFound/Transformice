package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_PlayerOpenShop implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseShopInstance().sendOpenShop(true);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}