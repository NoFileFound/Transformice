package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_OpenFashionSquadOutfitsWindow implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isGuest()) {
            client.closeConnection();
            client.getServer().getTempBlackList().add(client.getIpAddress());
            return;
        }

        if(client.getAccount().getPrivLevel() == 7 || client.getAccount().getPrivLevel() == 11 || !client.getAccount().getStaffRoles().contains("FashionSquad")) {
            client.getParseShopInstance().sendOpenFashionSquadOutfitsWindow();
        }
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 12;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}