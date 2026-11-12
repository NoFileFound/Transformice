package org.transformice.command.commands.debug;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaBindMouseClick;

@Command(name = "dbgmouse", description = "Tests the eventMouse.", permission = Command.CommandPermission.DEBUG_ONLY)
@SuppressWarnings("unused")
public final class Dbgmouse implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.isDebugMouse = !player.isDebugMouse;
        player.broadcastPacket(new PacketSendLuaBindMouseClick(player.isDebugMouse));
        CommandHandler.sendServerMessage(player, (player.isDebugMouse) ? "Debug mouse is enabled" : "Debug mouse is disabled");
    }
}