package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.send.login.C_PlayerAttack;

@SuppressWarnings("unused")
public final class S_PlayerAttack implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getRoom().sendAll(new C_PlayerAttack(client.getSessionId()));
        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventPlayerAttack", client.getPlayerName());
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}