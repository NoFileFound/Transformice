package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_RemoveCollectible;

@SuppressWarnings("unused")
public final class S_ReachCollectible implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int roundId = data.readByte();
        if(roundId == client.getRoom().getLastRoundId()) {
            int adventureId = data.readUnsignedByte();
            int individualId = data.readUnsignedShort();
            int x = data.readShort();
            int y = data.readShort();
            if(adventureId == 18 && Application.getPropertiesInfo().event.event_name.equals("Ninja")) {
                client.sendPacket(new C_RemoveCollectible(adventureId, individualId));

                client.getParseInventoryInstance().addConsumable("2611", 1, false);
                client.getParseInventoryInstance().addConsumable("2497", SrcRandom.RandomNumber(5, 9), false);
                client.getParseInventoryInstance().addConsumable("2257", SrcRandom.RandomNumber(1, 4), false);
            }

            if (client.getRoom().luaMinigame != null) {
                client.getRoom().luaApi.callEvent("eventPlayerReachCollectible", client.getPlayerName(), individualId, x, y);
            }
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 43;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}