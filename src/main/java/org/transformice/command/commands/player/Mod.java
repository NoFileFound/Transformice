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

@Command(name = "mod", description = "Lists the online moderators.", permission = Command.CommandPermission.PLAYER, aliases = {"mods"})
@SuppressWarnings("unused")
public final class Mod implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        Map<String, List<String>> staffMembers = new HashMap<>();
        for (GamePlayer client : server.getPlayers().values())
            if (client.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) && !client.hasStaffPermission(StaffRoleType.ADMIN, false))
                staffMembers.computeIfAbsent(client.getPlayerCommunity(), k -> new ArrayList<>()).add(client.getPlayerName());

        if (!staffMembers.isEmpty()) {
            StringBuilder staffMessage = new StringBuilder("$ModoEnLigne");
            for (Map.Entry<String, List<String>> entry : staffMembers.entrySet())
                staffMessage.append("<br>[").append(entry.getKey()).append("] <BV>").append(String.join("<BV>, </BV>", entry.getValue())).append("</BV>");

            CommandHandler.sendTranslatedMessage(player, "", staffMessage.toString(), new String[]{});
        } else {
            CommandHandler.sendTranslatedMessage(player, "", "$ModoPasEnLigne", new String[]{});
        }
    }
}