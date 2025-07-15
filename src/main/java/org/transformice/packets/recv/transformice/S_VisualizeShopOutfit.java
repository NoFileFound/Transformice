package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_VisualizeShopOutfit implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseShopInstance().sendVisualizeShopOutfit(data.readShort());
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 31;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}