package org.transformice.command.commands.player;

// Imports
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.command.Command;
import org.transformice.command.CommandHandler;
import org.transformice.packets.LegacyPacketOpcodes;

// Packets
import org.transformice.packets.send.player.PacketSendCurrencyAwardPopup;

@Command(name = "facebook", description = "Info about the current map", permission = Command.CommandPermission.PLAYER)
@SuppressWarnings("unused")
public final class Facebook implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        if (!player.getPlayerData().isEnabledFacebookCommand) {
            player.broadcastPacket(new PacketSendCurrencyAwardPopup(0, 20));
            player.getPlayerData().shopCheese += 20;
            player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendOpenFacebookPage) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    return new PSByteArray();
                }
            });

            player.getPlayerData().isEnabledFacebookCommand = true;
        }
    }
}