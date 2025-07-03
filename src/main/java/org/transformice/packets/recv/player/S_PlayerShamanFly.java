package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.C_ShamanFly;

@SuppressWarnings("unused")
public final class S_PlayerShamanFly implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.isShaman) return;

        client.getRoom().sendAllOthers(client, new C_ShamanFly(client.getSessionId(), data.readBoolean()));
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}