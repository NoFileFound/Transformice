package org.transformice.packets.recv.chat;

// Imports
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.database.collections.Sanction;
import org.transformice.packets.RecvPacket;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;
import org.transformice.packets.send.chat.C_ServerMessage;

@SuppressWarnings("unused")
public final class S_ChatMessage implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        data.decryptPacket(Application.getSwfInfo().packet_keys, fingerPrint);

        String message = Utils.formatText(data.readString().replace("&amp;#", "&#").replace("<", "&lt;"));
        if(client.isGuest()) {
            client.sendPacket(new C_TranslationMessage("", "$Créer_Compte_Parler"));
            return;
        }

        if(client.isHidden) {
            client.sendPacket(new C_ServerMessage(true, "You can't send chat messages while watching somebody"));
            return;
        }

        if(client.currentMessage.equals(message)) {
            client.sendPacket(new C_TranslationMessage("", "$Message_Identique"));
            return;
        }

        if(client.chatMessageTimer.getRemainingTime() <= 0) {
            Sanction mySanction = client.getServer().getLatestSanction(client.getPlayerName(), "mutedef");
            if(mySanction == null) {
                mySanction = client.getServer().getLatestSanction(client.getPlayerName(), "mutejeu");
            }

            if(mySanction != null) {
                long hours = (mySanction.getExpirationDate() - Utils.getUnixTime()) / 3600;
                client.sendPacket(new C_TranslationMessage("", "<ROSE>$MuteInfo1", new String[]{String.valueOf(hours+1), mySanction.getReason()}));
                return;
            }

            if (client.getRoom().luaMinigame != null) {
                client.getRoom().luaApi.callEvent("eventChatMessage", client.getPlayerName(), message);
                if (message.startsWith("!")) {
                    client.getRoom().luaApi.callEvent("eventChatCommand", client.getPlayerName(), message.substring(1));
                    if (client.getRoom().getDisabledChatCommandsDisplay().contains(message.substring(1).split(" ")[0])) {
                        return;
                    }
                }
            }
            client.currentMessage = message;
            client.getRoom().sendChatMessage(client.getFunCorpNickname().isEmpty() ? client.getPlayerName() : client.getFunCorpNickname(), message, client.isMumuted);

            // #ChatLog
            Deque<String[]> messages = new ArrayDeque<>(60);
            messages.add(new String[]{new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), message});
            client.getServer().getChatMessages().putIfAbsent(client.getPlayerName(), new Object2ObjectOpenHashMap<>());
            client.getServer().getChatMessages().get(client.getPlayerName()).put(client.getRoomName(), messages);
            client.chatMessageTimer.schedule(() -> {}, TimeUnit.SECONDS);
        } else {
            client.sendPacket(new C_TranslationMessage("", "$Doucement"));
        }
    }

    @Override
    public int getC() {
        return 6;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}