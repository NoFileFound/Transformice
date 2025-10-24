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
        String playerName = data.readString();
        if(playerName.startsWith("*")) {
            return;
        }

        if(client.getServer().checkIsConnected(playerName)) {
            var player = client.getServer().getPlayers().get(playerName);
            client.sendPacket(new C_PlayerOpenAdventureInterface(player.getAccount().getAdventureList(), player.getPlayerName(), player.getAccount().getMouseLook(), player.getAccount().getMouseColor(), player.getAccount().getAdventurePoints(), player.getAccount().getTitleList().size(), player.getAccount().getShopBadges().size()));
        }
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