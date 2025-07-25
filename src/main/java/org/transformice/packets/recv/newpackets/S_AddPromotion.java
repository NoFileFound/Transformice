package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_AddPromotion implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isGuest()) {
            client.closeConnection();
            client.getServer().getTempBlackList().add(client.getIpAddress());
            return;
        }

        if(client.hasStaffPermission("FashionSquad", "Promotions")) {
            client.getParseShopInstance().sendAddPromotion(data.readString(), data.readString(), data.readString(), data.readByte());
        }
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 18;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}