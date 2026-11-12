package org.transformice.packets.recv.room;

// Imports
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSJson;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.data.objects.RoomType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;
import org.transformice.packets.send.room.PacketSendMusicVideo;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_MUSIC_VIDEO)
public final class PacketRecvMusicVideo extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        if (player == null)
            return;

        GameRoom room = player.getPlayerRoom();
        if (room.getRoomType() != RoomType.MUSIC)
            return;

        String url = psData.readUTF();
        String ytId = Utils.getYoutubeID(url);
        if (ytId == null) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_ErreurVideo"));
            return;
        }

        synchronized (room.getMusicPlaylist()) {
            if (room.getMusicPlaylist().stream().anyMatch(music -> music.get("By").equals(player.getPlayerName()))) {
                player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_VideoEnAttente"));
                return;
            }

            if (room.getMusicPlaylist().stream().anyMatch(music -> ytId.equals(music.get("VideoID")))) {
                player.broadcastPacket(new PacketSendTranslatedMessage("", "$DejaPlaylist"));
                return;
            }
        }

        HttpURLConnection conn = null;
        try {
            URI apiUri = URI.create("https://www.googleapis.com/youtube/v3/videos?id=" + ytId + "&key=" + gameServer.getConfig().getGameConfig().getYoutubeApiKey() + "&part=snippet,contentDetails");
            HttpRequest request = HttpRequest.newBuilder(apiUri).timeout(Duration.ofSeconds(5)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                gameServer.getLogger().warn("[MusicHandler] Please renew your youtube api key.");
                player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_ErreurVideo"));
                return;
            }

            JsonNode jsonResponse = PSJson.read(response.body());
            JsonNode items = jsonResponse.path("items");
            if (!items.isArray() || items.isEmpty()) {
                player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_ErreurVideo"));
                return;
            }

            JsonNode videoInfo = items.get(0);
            String durationStr = videoInfo.path("contentDetails").path("duration").asText();
            String title = videoInfo.path("snippet").path("title").asText();
            if (durationStr.isEmpty() || title.isEmpty()) {
                player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_ErreurVideo"));
                return;
            }

            int duration = (int) Duration.parse(durationStr).getSeconds();
            duration = Math.min(duration, 300);
            synchronized (room.getMusicPlaylist()) {
                if (room.getMusicPlaylist().stream().anyMatch(music -> music.get("By").equals(player.getPlayerName()))) {
                    player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_VideoEnAttente"));
                    return;
                }

                if (room.getMusicPlaylist().stream().anyMatch(music -> title.equals(music.get("Title")) || ytId.equals(music.get("VideoID")))) {
                    player.broadcastPacket(new PacketSendTranslatedMessage("", "$DejaPlaylist"));
                    return;
                }

                boolean startPlaying = room.getMusicPlaylist().isEmpty();
                Map<String, String> music = new HashMap<>();
                music.put("By", player.getPlayerName());
                music.put("Title", title);
                music.put("Duration", String.valueOf(duration));
                music.put("VideoID", ytId);
                room.getMusicPlaylist().add(music);
                player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_AjoutVideo", new String[]{String.valueOf(room.getMusicPlaylist().size())}));
                if (startPlaying) {
                    room.musicTime = 0;
                    room.canChangeMusic = true;
                    room.musicSkipVotes = 0;
                    room.isPlayingMusic = true;
                    room.getPlayers().values().forEach(roomPlayer -> roomPlayer.broadcastPacket(new PacketSendMusicVideo(music, 0)));
                    gameServer.getLogger().debug(String.format("[MusicHandler] Starting a song: %s (id : %s) in the room %s", title, ytId, room.getRoomName()));
                }
            }
        } catch (IOException e) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_ErreurVideo"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$ModeMusic_ErreurVideo"));
        }
    }
}