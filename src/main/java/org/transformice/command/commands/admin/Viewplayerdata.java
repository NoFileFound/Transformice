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
        name = "viewplayerdata",
        usage = "[moduleName] [fileName]",
        description = "Opens the file that is saved in given module.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        requiredArgs = 2
)
@SuppressWarnings("unused")
public final class Viewplayerdata implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        File foundFile = searchFile(new File("functions/playerDatas/module_" + args.getFirst() + "/"), args.get(1));
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