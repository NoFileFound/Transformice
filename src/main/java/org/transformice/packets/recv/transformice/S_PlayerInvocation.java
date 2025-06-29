package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.transformice.C_PlayerInvocation;

@SuppressWarnings("unused")
public final class S_PlayerInvocation implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isShaman) {
            int objectCode = data.readInt128();
            int posX = data.readInt128();
            int posY = data.readInt128();
            int angle = data.readInt128();
            String rotation = data.readString();
            boolean invocation = data.readBoolean();

            client.getRoom().sendAllOthers(client, new C_PlayerInvocation(client.getSessionId(), (short)objectCode, (short)posX, (short)posY, (short)angle, rotation, invocation));
            if (client.getRoom().luaMinigame != null) {
                client.getRoom().luaApi.callEvent("eventSummoningStart", client.getPlayerName(), objectCode, posX, posY, angle);
            }
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}