package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.C_PlayerOpenAdventureInterface;

@SuppressWarnings("unused")
public final class S_PlayerOpenAdventureInterface implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendPacket(new C_PlayerOpenAdventureInterface(client.getAccount().getAdventureList(), client.getPlayerName(), client.getAccount().getMouseLook(), client.getAccount().getAdventurePoints(), client.getAccount().getTitleList().size(), client.getAccount().getShopBadges().size()));
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 70;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}