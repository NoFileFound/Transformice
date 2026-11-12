package org.transformice.command.commands.modo;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "chatfilter", usage = "(list|del) [text]", description = "Sends the list of filtered words, adds a word or deletes a word from the chat filter.", permission = {Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Chatfilter implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String option = args.getFirst();
        List<String> chatFilter = server.getConfig().getGameConfig().getChatFilter();
        switch (option) {
            case "list" -> {
                StringBuilder builder = new StringBuilder("Filtered strings:<br>");
                for (String str : chatFilter)
                    builder.append(str).append("<br>");

                builder.append("<br>");
                CommandHandler.sendLogMessage(player, 0, builder.toString());
            }

            case "del" -> {
                String word = String.join(" ", args.subList(1, args.size()));
                if (chatFilter.remove(word)) {
                    CommandHandler.sendServerMessage(player, String.format("The string <N>[%s]</N> has been removed from the filter.", word));
                } else {
                    CommandHandler.sendServerMessage(player, String.format("The string <N>[%s]</N> is not in the filter.", word));
                }
            }

            default -> {
                String word = String.join(" ", args);
                if (chatFilter.contains(word)) {
                    CommandHandler.sendServerMessage(player, String.format("The string <N>[%s]</N> is already filtered (matches [%s]).", word, word));
                } else {
                    chatFilter.add(word);
                    CommandHandler.sendServerMessage(player, String.format("The string <N>[%s]</N> has been added to the filter.", word));
                }
            }
        }
    }
}