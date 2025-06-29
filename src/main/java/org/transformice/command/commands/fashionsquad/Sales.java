package org.transformice.command.commands.fashionsquad;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.newpackets.C_OpenFashionSquadSalesWindow;

@Command(
        name = "sales",
        description = "Opens the shop sales window.",
        permission = {Command.CommandPermission.FASHIONSQUAD, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Sales implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendPacket(new C_OpenFashionSquadSalesWindow(Application.getPromotionsInfo()));
    }
}