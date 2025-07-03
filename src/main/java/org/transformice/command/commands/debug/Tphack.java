package org.transformice.command.commands.debug;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;
import org.transformice.packets.send.lua.C_BindMouseDown;

@Command(
        name = "tphack",
        description = "",
        permission = {Command.CommandPermission.DEBUG_ONLY}
)
@SuppressWarnings("unused")
public final class Tphack implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.isDebugTeleport = !player.isDebugTeleport;
        player.sendPacket(new C_BindMouseDown(player.isDebugTeleport));
        player.sendPacket(new C_ServerMessage(true, (player.isDebugTeleport) ? "Teleportation is enabled" : "Teleportation is disabled"));
    }
}