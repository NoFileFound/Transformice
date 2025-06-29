package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@Command(
        name = "invkick",
        usage = "[playerName]",
        description = "Kicks the user who was invited to your tribe house.",
        permission = Command.CommandPermission.TRIBE,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Invkick implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Client playerClient = server.getPlayers().get(playerName);
        if(playerClient == null || server.getTribeByName(player.getAccount().getTribeName()).getTribeMembers().contains(playerName)) return;

        if(playerClient.getInvitedTribeHouses().contains(player.getAccount().getTribeName())) {
            player.sendPacket(new C_TranslationMessage("", "$InvTribu_AnnulationEnvoyee", new String[]{"<V>" + player.getPlayerName() + "</V>"}));
            playerClient.sendPacket(new C_TranslationMessage("", "$InvTribu_AnnulationRecue", new String[]{"<V>" + player.getPlayerName() + "</V>"}));
            if(player.getRoom().getRoomName().equals(playerClient.getRoom().getRoomName())) {
                playerClient.sendEnterRoom(player.getServer().getRecommendedRoom(""), "");
            }
            playerClient.getInvitedTribeHouses().remove(player.getAccount().getTribeName());
        }
    }
}