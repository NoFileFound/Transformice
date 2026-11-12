package org.transformice.packets.recv.chat;

// Imports
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.database.data.SanctionEntry;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.PacketSendServerMessage;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CHAT_MESSAGE)
public final class PacketRecvChatMessage extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        psData.decryptPacket(gameServer.getConfig().getSWFConfig().getPacketKeys(), psHeader.readByte());
        String message = gameServer.formatText(psData.readUTF());
        GamePlayer player = client.getPlayer();
        if (player.getIsGuest()) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$Créer_Compte_Parler"));
            return;
        }

        if (player.isHidden) {
            player.broadcastPacket(new PacketSendServerMessage(true, "You can't send chat messages while watching somebody"));
            return;
        }

        if (player.lastChatMessage.equals(message)) {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$Message_Identique"));
            return;
        }

        SanctionEntry mySanction = gameServer.getDatabase().loadLastSanction(player.getPlayerName(), SanctionEntry.SanctionType.SANCTION_MUTE);
        if (mySanction != null) {
			long currentTime = Utils.getUnixTime();
			if (mySanction.isPermanent || mySanction.getExpirationDate() > currentTime) {
				long hours = mySanction.isPermanent ? 9999 : Math.max(1, (mySanction.getExpirationDate() - currentTime) / 3600);
				player.broadcastPacket(new PacketSendTranslatedMessage("", "<ROSE>$MuteInfo1", new String[]{String.valueOf(hours), mySanction.getReason()}));
				return;
			}
        }

        if (player.getChatMessageTimer().getRemainingTime() <= 0) {
            player.lastChatMessage = message;
            player.getPlayerRoom().sendChatMessage(player.funcorpPlayerName.isEmpty() ? player.getPlayerName() : player.funcorpPlayerName, message, player.getIsMumuted());

            // #ChatLog
            Deque<String[]> messages = new ArrayDeque<>(60);
            messages.add(new String[]{new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), message});
            gameServer.getChatMessages().get(player.getPlayerName()).put(player.getRoomName(), messages);
            player.getChatMessageTimer().schedule();
        } else {
            player.broadcastPacket(new PacketSendTranslatedMessage("", "$Doucement"));
        }
    }
}