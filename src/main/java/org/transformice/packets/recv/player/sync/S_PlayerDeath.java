package org.transformice.packets.recv.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_AddBonus;
import org.transformice.packets.send.transformice.C_CleanMouseSkill;

@SuppressWarnings("unused")
public final class S_PlayerDeath implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isDead) return;

        int roundId = data.readInt();
        if(roundId == client.getRoom().getLastRoundId()) {
            int deathId = data.readUnsignedByte();

            client.sendPlayerDeath();
            if (client.getRoom().getCurrentShaman() != null && client.getRoom().getCurrentShaman().isDisintegration) {
                client.getRoom().sendAll(new C_AddBonus(6, client.getPosition().getFirst(), 395, 0, 0, true));
            }

            if (client.getRoom().getCurrentShaman() != null && client.getRoom().getCurrentShaman().bubblesCount > 0) {
                client.getRoom().getCurrentShaman().bubblesCount--;
                client.sendPacket(new C_CleanMouseSkill(client.getPosition().getFirst()));
                client.getRoom().sendPlaceObject(client.getRoom().getLastObjectID() + 1, 59, client.getPosition().getFirst(), 450, 0, 0, 0, true, true, new byte[]{}, null, true);
            }

            client.getRoom().checkChangeMap();
            if (client.getRoom().luaMinigame != null) {
                client.getRoom().updatePlayerList(client);
                client.getRoom().luaApi.callEvent("eventPlayerDied", client.getPlayerName(), deathId);
            }
        }
    }

    @Override
    public int getC() {
        return 4;
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