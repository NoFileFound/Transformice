package org.transformice.command.commands.player;

// Imports
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.room.C_MusicVideo;

@Command(
        name = "skip",
        description = "Votes to skip the current music."
)
@SuppressWarnings("unused")
public final class Skip implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        if (player.skipMusicTimer.getRemainingTime() <= 0) {
            if(player.getRoom().isMusic() && player.getRoom().isPlayingMusic) {
                player.getRoom().musicSkipVotes++;
                int count = player.getRoom().getPlayersCount();
                count = count % 2 == 0 ? count : count + 1;
                if (player.getRoom().musicSkipVotes >= count / 2) {
                    player.getRoom().getMusicVideos().removeFirst();
                    if(!player.getRoom().getMusicVideos().isEmpty()) {
                        player.getRoom().getPlayers().values().forEach(_player -> _player.sendPacket(new C_MusicVideo(player.getRoom().getMusicVideos().getFirst(), player.getRoom().getMusicTime())));
                    }
                }

                player.skipMusicTimer.schedule(() -> {}, TimeUnit.SECONDS);
            }
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("musicskipratelimited", player.skipMusicTimer.getRemainingTime()));
        }
    }
}