package org.transformice.command.commands.arbitre;

// Imports
import static org.transformice.utils.Utils.formatUnixTime;
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Sanction;

@Command(
        name = "casier",
        usage = "[playerName]",
        description = "Lists bans received by a player.",
        permission = {Command.CommandPermission.ARBITRE, Command.CommandPermission.TRIALMODO, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR},
        aliases = {"sanctions"}
)
@SuppressWarnings("unused")
public final class Casier implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = (!args.isEmpty()) ? args.getFirst() : player.getPlayerName();
        List<Sanction> sanctionList = DBUtils.findSanctionsByAccount(playerName);
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("<p align='center'><N>Sanction Logs for <V>%s</V></N></p><br><br><p align='left'>Currently running sanctions: </p><br>", playerName));
        for(Sanction sanction : sanctionList) {
            String beginDate = formatUnixTime(sanction.getCreatedDate(), "MM/dd/yyyy HH:mm:ss");
            String endDate = formatUnixTime(sanction.getExpirationDate(), "MM/dd/yyyy HH:mm:ss");
            long hours = (sanction.getExpirationDate() - sanction.getCreatedDate()) / 3600;

            if(sanction.getIsPermanent()) {
                builder.append(String.format("- <G><V>%s (permanent) </V></V> <N>(%s) by %s</N> : <BL>%s</BL><br>", sanction.getType().replace("jeu", "").replace("def", "").toUpperCase(), sanction.getIpAddress(), sanction.getAuthor(), sanction.getReason()));
            } else {
                builder.append(String.format("- <G><V>%s %dh </V></V> <N>(%s) by %s</N> : <BL>%s</BL><br>", sanction.getType().replace("jeu", "").replace("def", "").toUpperCase(), hours, sanction.getIpAddress(), sanction.getAuthor(), sanction.getReason()));
            }

            if(sanction.getState().equals("Expired")) {
                builder.append(String.format("  <p align='left'><font size='9'><G>%s → %s</G></font></p><br>", beginDate, endDate));
            } else if(sanction.getState().equals("Active")) {
                builder.append(String.format("  <p align='left'><font size='9'><N2>%s → %s</N2></font></p><br>", beginDate, endDate));
            } else {
                builder.append(String.format("<p align='left'><font size='9'><BL>    Cancelled by %s", sanction.getCancelAuthor()));
                if(!sanction.getCancelReason().isEmpty()) {
                    builder.append(String.format(" : %s</BL></font></p><br>", sanction.getCancelReason()));
                } else {
                    builder.append("</BL></font></p><br>");
                }
            }
            builder.append("</G>");
        }

        if(sanctionList.isEmpty()) {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("casierless"));
        } else {
            CommandHandler.sendLogMessage(player, 0, builder.toString());
        }
    }
}