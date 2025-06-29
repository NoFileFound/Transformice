package org.transformice.command;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;
import org.transformice.packets.send.informations.C_LogMessage;
import org.transformice.packets.send.informations.C_TranslationMessage;

public interface CommandHandler {
    /**
     * Send a #Server message to the target.
     *
     * @param player The player to send the message to.
     * @param message The message to send.
     */
    static void sendServerMessage(Client player, String message) {
        player.sendPacket(new C_ServerMessage(true, message));
    }

    /**
     * Send a #Server message to the target.
     *
     * @param player The player to send the message to.
     * @param message The message to send.
     * @param arguments The message arguments.
     */
    static void sendServerMessage(Client player, String message, String ... arguments) {
        player.sendPacket(new C_ServerMessage(true, message, List.of(arguments)));
    }

    /**
     * Sends a translated message to the target.
     * @param player The player to send the message.
     * @param community The player's community.
     * @param message The player's message.
     * @param args The message args.
     */
    static void sendTranslatedMessage(Client player, String community, String message, String[] args) {
        player.sendPacket(new C_TranslationMessage(community, message, args));
    }

    /**
     * Sends a window message to the target.
     * @param player The player to send the message.
     * @param font The font.
     * @param message The message.
     */
    static void sendLogMessage(Client player, int font, String message) {
        player.sendPacket(new C_LogMessage(font, message));
    }

    /**
     * Handles the current command.
     * @param player The player who ran the command.
     * @param args The command arguments.
     */
    void execute(Client player, Server server, List<String> args);
}