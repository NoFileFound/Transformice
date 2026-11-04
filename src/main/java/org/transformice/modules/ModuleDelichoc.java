package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.database.data.PlayerEntity;
import org.transformice.modules.impl.ModuleServer;

public final class ModuleDelichoc extends ModuleServer {
    /**
     * Creates a new delichoc module.
     *
     * @param server The game's server.
     */
    public ModuleDelichoc(GameServer server) {
        super(server);
    }

    /**
     * Handles the delichoc commands.
     * @param rawMessage The raw message.
     */
    public void handleDelichocCommand(GamePlayer player, String rawMessage) {
        String[] split = rawMessage.trim().split("\\s+");
        String commandName = split[0].toLowerCase();
        List<String> args = new ArrayList<>(Arrays.asList(split).subList(1, split.length));
        switch (commandName) {
            case ".avatar": {
                if (args.isEmpty()) {
                    this.sendDelichocMessage("You need more arguments to use this command.");
                    break;
                }

                PlayerEntity client = this.server.getDatabase().loadPlayerByName(args.getFirst());
                if (client == null) {
                    this.sendDelichocMessage("The supplied argument is not a valid nickname.");
                    break;
                }

                long id = client.getAccountId();
                this.sendDelichocMessage(String.format("%s's avatar: https://avatars.atelier801.com/%s/%s.jpg", client.getUsername(), id % 10000, id));
                break;
            }
            case ".ping": {
                this.sendDelichocMessage("Pong!");
                break;
            }
        }
    }

    /**
     * Sends a #Modo message.
     * @param message The message to send.
     */
    private void sendDelichocMessage(String message) {
        this.server.sendStaffChannelMessage((byte) 4, message, true, "Delichoc", "FR");
    }
}