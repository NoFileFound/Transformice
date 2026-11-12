package org.transformice.command;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;

// Packets
import org.transformice.packets.send.chat.PacketSendServerMessage;
import org.transformice.packets.send.informations.PacketSendLogMessage;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

public interface CommandHandler {
    /**
     * Send a #Server message to the target.
     *
     * @param player The player to send the message to.
     * @param message The message to send.
     */
    static void sendServerMessage(GamePlayer player, String message) {
        player.broadcastPacket(new PacketSendServerMessage(true, message));
    }

    /**
     * Send a #Server message to the target.
     *
     * @param player The player to send the message to.
     * @param message The message to send.
     * @param arguments The message arguments.
     */
    static void sendServerMessage(GamePlayer player, String message, String[] arguments) {
        player.broadcastPacket(new PacketSendServerMessage(true, message, List.of(arguments)));
    }

    /**
     * Sends a translated message to the target.
     * @param player The player to send the message.
     * @param community The player's community.
     * @param message The player's message.
     * @param args The message args.
     */
    static void sendTranslatedMessage(GamePlayer player, String community, String message, String[] args) {
        player.broadcastPacket(new PacketSendTranslatedMessage(community, message, args));
    }

    /**
     * Sends a window message to the target.
     * @param player The player to send the message.
     * @param font The font.
     * @param message The message.
     */
    static void sendLogMessage(GamePlayer player, int font, String message) {
        player.broadcastPacket(new PacketSendLogMessage(font, message));
    }

    /**
     * Handles the current command.
     * @param player The player who ran the command.
     * @param args The command arguments.
     */
    void execute(GamePlayer player, GameServer server, List<String> args);
}