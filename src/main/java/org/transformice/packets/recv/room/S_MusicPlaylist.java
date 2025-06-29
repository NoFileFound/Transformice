package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_MusicPlaylist;

@SuppressWarnings("unused")
public final class S_MusicPlaylist implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendPacket(new C_MusicPlaylist(client.getRoom().getMusicVideos()));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 73;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}