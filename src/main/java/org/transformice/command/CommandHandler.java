package org.transformice.command;

// Imports
import java.util.List;
import org.transformice.Client;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;

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
     * Handles the current command.
     * @param player The player who ran the command.
     * @param args The command arguments.
     */
    void execute(Client player, List<String> args);
}