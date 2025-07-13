package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.login.C_DespawnMonster;
import org.transformice.packets.send.login.C_SetMonsterHit;

@SuppressWarnings("unused")
public final class S_PlayerHitMonster implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int monsterId = data.readInt();
        boolean right = data.readBoolean();

        if(!client.getRoom().getMonsterLifes().containsKey(monsterId)) {
            client.closeConnection();
            return;
        }

        client.getRoom().getMonsterLifes().put(monsterId, client.getRoom().getMonsterLifes().get(monsterId) - 1);
        if(client.getRoom().getMonsterLifes().get(monsterId) <= 0) {
            client.getRoom().sendAll(new C_DespawnMonster(monsterId));
        } else {
            client.getRoom().sendAll(new C_SetMonsterHit(monsterId, right));
        }

        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventPlayerHitMonster", client.getPlayerName());
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}