package org.transformice.command.commands.debug;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaBindKeyboard;

@Command(name = "dbgkeyboard", description = "Tests the eventKeyboard.", requiredArgs = 1, permission = Command.CommandPermission.DEBUG_ONLY)
@SuppressWarnings("unused")
public final class Dbgkeyboard implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.isDebugKeyboard = !player.isDebugKeyboard;
        player.broadcastPacket(new PacketSendLuaBindKeyboard(Integer.parseInt(args.getFirst()), true, player.isDebugKeyboard));
        CommandHandler.sendServerMessage(player, (player.isDebugKeyboard) ? "Debug keyboard is enabled" : "Debug keyboard is disabled");
    }
}