package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;
import org.transformice.packets.send.tribe.C_TribeHouseInvitation;

@Command(
        name = "inv",
        usage = "[playerName]",
        description = "Invites the user to your tribe house.",
        permission = Command.CommandPermission.TRIBE,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Inv implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String playerName = args.getFirst();
        Client playerClient = server.getPlayers().get(playerName);
        if(playerClient == null || server.getTribeByName(player.getAccount().getTribeName()).getTribeMembers().contains(playerName)) return;
        if(!playerClient.getInvitedTribeHouses().contains(player.getAccount().getTribeName())) {
            player.sendPacket(new C_TranslationMessage("", "$InvTribu_InvitationEnvoyee", new String[]{"<V>" + playerName + "</V>"}));
            playerClient.sendPacket(new C_TribeHouseInvitation(player.getPlayerName(), player.getAccount().getTribeName()));
            playerClient.getInvitedTribeHouses().add(player.getAccount().getTribeName());
        }
    }
}