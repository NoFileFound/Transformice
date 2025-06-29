package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.C_PlayerMeep;

@SuppressWarnings("unused")
public final class S_PlayerMeep implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int x = data.readInt128();
        int y = data.readInt128();
        if(client.canMeep) {
            client.getRoom().sendAll(new C_PlayerMeep(client.getSessionId(), x, y, client.isShaman ? 20 : 5));
        }

        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventPlayerMeep", client.getPlayerName(), x, y);
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 39;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}