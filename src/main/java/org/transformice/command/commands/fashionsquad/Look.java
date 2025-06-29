package org.transformice.command.commands.fashionsquad;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.collections.Account;

@Command(
        name = "look",
        description = "Gets the look of specific player name.",
        usage = "(playerName)",
        permission = {Command.CommandPermission.FASHIONSQUAD, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Look implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = (args.isEmpty()) ? player.getPlayerName() : args.getFirst();

        Account myAccount = server.getPlayerAccount(playerName);
        if(myAccount == null) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
            return;
        }

        CommandHandler.sendServerMessage(player, playerName + "'s look: " + myAccount.getMouseLook());
    }
}