package org.transformice.command.commands.admin;

// Imports
import static org.transformice.utils.Utils.searchFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "viewmodule",
        usage = "[moduleName]",
        description = "Opens the source code of given mini game.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Viewmodule implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        File foundFile = searchFile(new File("functions/minigames/"), args.getFirst() + ".functions");
        if (foundFile != null) {
            StringBuilder message = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(foundFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    message.append(line).append("\n");
                }
                CommandHandler.sendLogMessage(player, 1, message.toString());
            } catch (IOException e) {
                CommandHandler.sendServerMessage(player, e.getMessage());
            }
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("filenotfound"));
        }
    }
}