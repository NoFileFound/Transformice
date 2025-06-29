package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_SpawnObject;

@SuppressWarnings("unused")
public final class S_PlaceIceBlock implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int sessionId = data.readInt();
        int posX = data.readShort();
        int posY = data.readShort();
        if (client.isShaman && !client.isDead && client.getRoom().getNumCompleted() > 1) {
            if (client.iceCount != 0 && sessionId != client.getSessionId()) {
                for (Client player : client.getRoom().getPlayers().values()) {
                    if (player.getSessionId() == sessionId && !player.isShaman) {
                        player.sendPlayerDeath();
                        client.getRoom().sendAll(new C_SpawnObject(client.getRoom().getLastObjectID() + 1, 54, posX, posY, 0, 0, 0, false, true, new byte[]{}));
                        client.iceCount--;
                    }
                }
            }
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}