package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(
        name = "chatfilter",
        usage = "(list|del) [text]",
        description = "Sends the list of filtered words, adds a word or deletes a word from the chat filter.",
        permission = {Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Chatfilter implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String option = args.getFirst();
        if(option.equals("list")) {
            StringBuilder builder = new StringBuilder();
            builder.append("Filtered strings:<br>");
            for(String str : Application.getBadWordsConfig()) {
                builder.append(str).append("<br>");
            }
            builder.append("<br>");
            CommandHandler.sendLogMessage(player, 0, builder.toString());
        } else if(option.equals("del")) {
            String word = String.join(" ", args.subList(1, args.size()));
            if(Application.getBadWordsConfig().contains(word)) {
                Application.getBadWordsConfig().remove(word);
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("chatfilter_removed", word));
            } else {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("chatfilter_notfound", word));
            }
        } else {
            String word = String.join(" ", args);
            if(Application.getBadWordsConfig().contains(word)) {
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("chatfilter_already", word, word));
            } else {
                Application.getBadWordsConfig().add(word);
                CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("chatfilter_added", word));
            }
        }
    }
}