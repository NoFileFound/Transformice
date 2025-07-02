package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.legacy.editor.C_InitMapEditor;

@Command(
        name = "editor",
        description = "Sends you to the Map Editor.",
        permission = Command.CommandPermission.PLAYER,
        aliases = {"editeur"}
)
@SuppressWarnings("unused")
public final class Editor implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendEnterRoom((char)3 + "[Editeur] " + player.getPlayerName(), "");
        player.sendOldPacket(new C_InitMapEditor(-1));
    }
}