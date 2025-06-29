package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.transformice.C_PlayerStopInvocation;

@SuppressWarnings("unused")
public final class S_PlayerStopInvocation implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isShaman) {
            client.getRoom().sendAllOthers(client, new C_PlayerStopInvocation(client.getSessionId()));
            if (client.getRoom().luaMinigame != null) {
                client.getRoom().luaApi.callEvent("eventSummoningCancel", client.getPlayerName());
            }
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}