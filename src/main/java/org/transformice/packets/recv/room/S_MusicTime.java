package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.send.room.C_MusicVideo;

@SuppressWarnings("unused")
public final class S_MusicTime implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int time = data.readInt();
        if (!client.getRoom().getMusicVideos().isEmpty()) {
            client.getRoom().setMusicTime((short)time);
            String duration = client.getRoom().getMusicVideos().getFirst().get("Duration");
            if (time >= Integer.parseInt(duration) - 5 && client.getRoom().canChangeMusic) {
                client.getRoom().canChangeMusic = false;
                client.getRoom().getMusicVideos().removeFirst();
                if (!client.getRoom().getMusicVideos().isEmpty()) {
                    client.getRoom().getPlayers().values().forEach(player -> player.sendPacket(new C_MusicVideo(client.getRoom().getMusicVideos().getFirst(), client.getRoom().getMusicTime())));
                } else {
                    client.getRoom().isPlayingMusic = false;
                    client.getRoom().setMusicTime((short)0);
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
        return 71;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}