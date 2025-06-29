package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_CollectDefilantePoint implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int pointId = data.readInt();
        if(pointId < 0) {
            client.closeConnection();
            return;
        }

        client.defilantePoints += 1;
        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventPlayerBonusGrabbed", client.getPlayerName(), pointId);
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 25;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}