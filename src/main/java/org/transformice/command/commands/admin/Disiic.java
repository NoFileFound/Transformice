package org.transformice.command.commands.admin;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.newpackets.C_DisableInitialItemCooldown;

@Command(
        name = "disiic",
        usage = "[playerName]",
        description = "Disables the consumable cooldown on given player.",
        permission = Command.CommandPermission.ADMINISTRATOR,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Disiic implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        if(player.getServer().getPlayers().get(playerName) != null) {
            player.sendPacket(new C_DisableInitialItemCooldown());
            CommandHandler.sendServerMessage(player, "Done!");
        } else {
            CommandHandler.sendServerMessage(player, Application.getTranslationManager().get("invalidusername"));
        }
    }
}