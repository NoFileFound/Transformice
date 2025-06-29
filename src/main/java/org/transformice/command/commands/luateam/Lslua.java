package org.transformice.command.commands.luateam;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.level.C_OnlineStaffTeam;

@Command(
        name = "lslua",
        description = "Lists connected functions crews and their channels.",
        permission = {Command.CommandPermission.LUADEV, Command.CommandPermission.MODERATOR, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Lslua implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        List<String> staffInfo = new ArrayList<>();
        for(Client client : server.getPlayers().values()) {
            if(!client.isGuest() && client.getAccount().getPrivLevel() == 6 || client.getAccount().getStaffRoles().contains("LuaDev")) {
                staffInfo.add(client.playerCommunity + "_" + client.getPlayerName() + "_" + client.getRoomName());
            }
        }
        player.sendPacket(new C_OnlineStaffTeam(8, staffInfo));
    }
}