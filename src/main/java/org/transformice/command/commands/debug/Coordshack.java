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
        name = "coordshack",
        description = "Manages you to get the coordinate of every click.",
        permission = {Command.CommandPermission.DEBUG_ONLY}
)
@SuppressWarnings("unused")
public final class Coordshack implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.isDebugCoords = !player.isDebugCoords;
        player.sendPacket(new C_BindMouseDown(player.isDebugCoords));
        player.sendPacket(new C_ServerMessage(true, (player.isDebugCoords) ? "Coords is enabled" : "Coords is disabled"));
    }
}