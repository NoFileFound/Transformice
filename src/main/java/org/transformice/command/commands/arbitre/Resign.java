package org.transformice.command.commands.arbitre;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.PlayerEntity;

@Command(name = "resign", description = "It serves to resign as an arbiter.", permission = {Command.CommandPermission.ARBITRE})
@SuppressWarnings("unused")
public final class Resign implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        PlayerEntity entity = player.getPlayerData();
        if (!entity.getStaffRoles().contains(StaffRoleType.ARBITRE))
            return;

        entity.getStaffRoles().remove(StaffRoleType.ARBITRE);
        server.sendStaffChannelMessage((byte) 2, String.format("%s has resigned.", player.getPlayerName()), true, player.getPlayerName(), player.getPlayerCommunity());
        player.disconnect(true);
    }
}