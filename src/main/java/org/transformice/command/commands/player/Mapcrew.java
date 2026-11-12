package org.transformice.command.commands.player;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;

@Command(name = "mapcrew", description = "Lists the online map crews.", permission = Command.CommandPermission.PLAYER, aliases = {"mapcrews"})
@SuppressWarnings("unused")
public final class Mapcrew implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        Map<String, List<String>> staffMembers = new HashMap<>();
        for (GamePlayer client : server.getPlayers().values())
            if (client.hasStaffPermission(StaffRoleType.MAPCREW, false) && !client.hasStaffPermission(StaffRoleType.ADMIN, false))
                staffMembers.computeIfAbsent(client.getPlayerCommunity(), k -> new ArrayList<>()).add(client.getPlayerName());

        if (!staffMembers.isEmpty()) {
            StringBuilder staffMessage = new StringBuilder("$MapcrewEnLigne");
            for (Map.Entry<String, List<String>> entry : staffMembers.entrySet())
                staffMessage.append("<br>[").append(entry.getKey()).append("] <BV>").append(String.join("<BV>, </BV>", entry.getValue())).append("</BV>");

            CommandHandler.sendTranslatedMessage(player, "", staffMessage.toString(), new String[]{});
        } else {
            CommandHandler.sendTranslatedMessage(player, "", "$MapcrewPasEnLigne", new String[]{});
        }
    }
}