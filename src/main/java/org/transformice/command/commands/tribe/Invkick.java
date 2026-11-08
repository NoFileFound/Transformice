package org.transformice.command.commands.tribe;

// Imports
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

@Command(name = "invkick", usage = "[playerName]", description = "Kicks the user who was invited to your tribe house.", permission = Command.CommandPermission.TRIBE, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Invkick implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        String playerName = args.getFirst();
        String tribeName = player.getPlayerData().tribeName;
        GamePlayer target = server.getPlayers().get(playerName);
        if (target == null || server.getDatabase().loadTribeByName(tribeName).getTribeMembers().contains(playerName))
            return;

        if (!target.getInvitedTribeHouses().remove(tribeName))
            return;

        String formattedName = "<V>" + player.getPlayerName() + "</V>";
        player.broadcastPacket(new PacketSendTranslatedMessage("", "$InvTribu_AnnulationEnvoyee", new String[]{formattedName}));
        target.broadcastPacket(new PacketSendTranslatedMessage("", "$InvTribu_AnnulationRecue", new String[]{formattedName}));
        if (target.getPlayerRoom().getRoomNameSuffix().equals("*\u0003" + tribeName))
            target.sendEnterRoom(server.getRecommendedRoom("", player.getPlayerCommunity()), "");
    }
}