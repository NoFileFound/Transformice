package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;

// Packets
import org.transformice.packets.send.login.C_MoneyEarned;
import org.transformice.packets.send.player.C_PlayerReward;

@Command(
        name = "codecadeau",
        description = "",
        permission = Command.CommandPermission.PLAYER,
        requiredArgs = 1
)
@SuppressWarnings("unused")
public final class Codecadeau implements CommandHandler {
    @Override
    public void execute(Client player, Server server, List<String> args) {
        String code = args.getFirst();
        var info = Application.getJapanExpoInfo().get(code);
        if(info == null) return;

        for(var entry : info.prize) {
            if(entry.containsKey("cheeses")) {
                player.sendPacket(new C_MoneyEarned(entry.get("cheeses"), 0));
                player.sendPacket(new C_PlayerReward(0, entry.get("cheeses")));
                player.getAccount().setShopCheeses(player.getAccount().getShopCheeses() + (entry.get("cheeses")));
            }

            if(entry.containsKey("fraises")) {
                player.sendPacket(new C_MoneyEarned(0, (entry.get("fraises"))));
                player.sendPacket(new C_PlayerReward(1, entry.get("fraises")));
                player.getAccount().setShopStrawberries(player.getAccount().getShopStrawberries() + (entry.get("fraises")));
            }

            if(entry.containsKey("consumable")) {
                player.getParseInventoryInstance().addConsumable(String.valueOf(entry.get("consumable")), 50, true);
            }
        }

        Application.getJapanExpoInfo().remove(code);
    }
}