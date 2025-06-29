package org.transformice.packets.recv.room;

// Imports
import static org.transformice.utils.Utils.getYoutubeID;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;
import org.transformice.packets.send.room.C_MusicVideo;

@SuppressWarnings("unused")
public final class S_SendMusicVideo implements RecvPacket {
    private final Gson gson = new Gson();

    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        String url = data.readString();
        String ytId = getYoutubeID(url);
        if(ytId == null) {
            client.sendPacket(new C_TranslationMessage("", "$ModeMusic_ErreurVideo"));
            return;
        }

        if (client.getRoom().getMusicVideos().stream().anyMatch(music -> music.get("By").equals(client.getPlayerName()))) {
            client.sendPacket(new C_TranslationMessage("", "$ModeMusic_VideoEnAttente"));
            return;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.googleapis.com/youtube/v3/videos?id=" + ytId + "&key=" + Application.getPropertiesInfo().yt_key + "&part=snippet,contentDetails").openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject jsonResponse = this.gson.fromJson(response.toString(), JsonObject.class);
            if(jsonResponse != null) {
                JsonArray items = jsonResponse.getAsJsonArray("items");
                if (items.isEmpty()) {
                    client.sendPacket(new C_TranslationMessage("", "$ModeMusic_ErreurVideo"));
                    return;
                }

                JsonObject videoInfo = items.get(0).getAsJsonObject();
                String durationStr = videoInfo.getAsJsonObject("contentDetails").get("duration").getAsString();
                int duration = (int) Duration.parse(durationStr).getSeconds();
                duration = Math.min(duration, 300);
                String title = videoInfo.getAsJsonObject("snippet").get("title").getAsString();
                if (client.getRoom().getMusicVideos().stream().anyMatch(music -> music.get("Title").equals(title))) {
                    client.sendPacket(new C_TranslationMessage("", "$DejaPlaylist"));
                    return;
                }

                client.sendPacket(new C_TranslationMessage("", "$ModeMusic_AjoutVideo", new String[] {String.valueOf(client.getRoom().getMusicVideos().size() + 1)}));
                Map<String, String> values = new HashMap<>();
                values.put("By", client.getPlayerName());
                values.put("Title", title);
                values.put("Duration", String.valueOf(duration));
                values.put("VideoID", ytId);
                client.getRoom().getMusicVideos().add(values);
                if (client.getRoom().getMusicVideos().size() == 1) {
                    client.getRoom().getPlayers().values().forEach(player -> player.sendPacket(new C_MusicVideo(client.getRoom().getMusicVideos().getFirst(), client.getRoom().getMusicTime())));
                    client.getRoom().isPlayingMusic = true;
                    client.getRoom().musicSkipVotes = 0;
                }
            } else {
                client.sendPacket(new C_TranslationMessage("", "$ModeMusic_ErreurVideo"));
            }
        } catch(IOException ignored) {
            client.sendPacket(new C_TranslationMessage("", "$ModeMusic_ErreurVideo"));
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 70;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}