package org.transformice.command.commands.arbitre;

// Imports
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.data.SanctionEntry;

@Command(name = "casier", usage = "[playerName]", description = "Lists bans and mutes received by a player.", permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}, aliases = {"sanctions"})
@SuppressWarnings("unused")
public final class Casier implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.isEmpty() ? player.getPlayerName() : args.getFirst();
        List<SanctionEntry> sanctionList = server.getDatabase().loadSanctionsByPlayer(playerName);
        if (sanctionList.isEmpty()) {
            CommandHandler.sendServerMessage(player, "The nickname has no recently sanctions.");
            return;
        }

        StringBuilder builder = new StringBuilder(String.format("<p align='center'><N>Sanction Logs for <V>%s</V></N></p><br><br><p align='left'>Currently running sanctions:</p><br>", playerName));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
        for (SanctionEntry sanction : sanctionList) {
            long hours = (sanction.getExpirationDate() - sanction.getCreatedDate()) / 3600;
            builder.append(String.format("- <G><V>%s%s</V></V> <N>(%s) by %s</N> : <BL>%s</BL><br>", sanction.getSanctionType(), sanction.isPermanent ? " (permanent)" : " " + hours + "h", sanction.getIpAddress(), sanction.getAuthor(), sanction.getReason()));
            switch (sanction.sanctionState) {
                case EXPIRED -> builder.append(String.format("<p align='left'><font size='9'><G>%s → %s</G></font></p><br>", formatter.format(Instant.ofEpochSecond(sanction.getCreatedDate())), formatter.format(Instant.ofEpochSecond(sanction.getExpirationDate()))));
                case ACTIVE -> builder.append(String.format("<p align='left'><font size='9'><N2>%s → %s</N2></font></p><br>", formatter.format(Instant.ofEpochSecond(sanction.getCreatedDate())), formatter.format(Instant.ofEpochSecond(sanction.getExpirationDate()))));
                default -> {
                    builder.append(String.format("<p align='left'><font size='9'><BL>    Cancelled by %s", sanction.cancelAuthor));
                    if (!sanction.cancelReason.isEmpty())
                        builder.append(String.format(" : %s", sanction.cancelReason));

                    builder.append("</BL></font></p><br>");
                }
            }

            builder.append("</G>");
        }

        CommandHandler.sendLogMessage(player, 0, builder.toString());
    }
}