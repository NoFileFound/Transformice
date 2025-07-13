package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.send.login.C_PlayerDamaged;

@SuppressWarnings("unused")
public final class S_PlayerDamaged implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(Application.getPropertiesInfo().event.event_name.equals("Halloween")) {
            client.playerHealth--;
            if (client.playerHealth <= 0) {
                client.playerHealth = 0;
                client.sendPlayerDeath();
            }
        }

        client.getRoom().sendAll(new C_PlayerDamaged(client.getSessionId()));
        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventPlayerDamaged", client.getPlayerName());
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 11;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}