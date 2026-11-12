package org.transformice.command.commands.player;

// Imports
import java.util.List;
import java.util.regex.Pattern;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

@Command(name = "pw", description = "Changes the password of current room.", permission = Command.CommandPermission.ROOM_OWNER)
@SuppressWarnings("unused")
public final class Pw implements CommandHandler {
    private static final Pattern ROOM_NAME_PATTERN = Pattern.compile("^(?!.*(?:bootcamp|racing|defilante|vanilla|survivor|music|village)(?:\\d+)?(?=$|\\W)).*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String password = String.join(" ", args);
        if (ROOM_NAME_PATTERN.matcher(player.getPlayerRoom().getRoomNameSuffix()).matches() && !player.getPlayerRoom().getRoomNameSuffix().matches("\\d+")) {
            player.getPlayerRoom().roomPassword = password;
            CommandHandler.sendTranslatedMessage(player, "", (password.isEmpty()) ? "$MDP_Desactive" : "$Mot_De_Passe : " + password, new String[]{});
        }
    }
}