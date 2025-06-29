package org.transformice.command.commands.player;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@Command(
        name = "mapcrew",
        description = "Lists the online map crews.",
        permission = Command.CommandPermission.PLAYER,
        aliases = {"mapcrews"}
)
@SuppressWarnings("unused")
public final class Mapcrew implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        Map<String, List<String>> staffMembers = new HashMap<>();
        for(Client client : server.getPlayers().values()) {
            if(!client.isGuest() && (client.getAccount().getPrivLevel() == 8 || client.getAccount().getStaffRoles().contains("MapCrew"))) {
                staffMembers.computeIfAbsent(client.playerCommunity, k -> new ArrayList<>()).add(client.getPlayerName());
            }
        }

        if (!staffMembers.isEmpty()) {
            StringBuilder staffMessage = new StringBuilder("$MapcrewEnLigne");
            for (Map.Entry<String, List<String>> entry : staffMembers.entrySet()) {
                staffMessage.append("<br>[").append(entry.getKey()).append("] <BV>").append(String.join("<BV>, </BV>", entry.getValue())).append("</BV>");
            }

            player.sendPacket(new C_TranslationMessage("", staffMessage.toString()));
        } else {
            player.sendPacket(new C_TranslationMessage("", "$MapcrewPasEnLigne"));
        }
    }
}