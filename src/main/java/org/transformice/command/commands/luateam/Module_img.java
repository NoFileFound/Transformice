package org.transformice.command.commands.luateam;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.newpackets.C_UploadModuleImage;

@Command(
        name = "module_img",
        description = "Uploads an module image.",
        permission = {Command.CommandPermission.LUADEV, Command.CommandPermission.ADMINISTRATOR}
)
@SuppressWarnings("unused")
public final class Module_img implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        player.sendPacket(new C_UploadModuleImage());
    }
}