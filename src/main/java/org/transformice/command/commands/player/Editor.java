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
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

@Command(name = "editor", description = "Sends you to the Map Editor.", permission = Command.CommandPermission.PLAYER, aliases = {"editeur"})
@SuppressWarnings("unused")
public final class Editor implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        player.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapEditorEnterRoom) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return new NetByteArray();
            }
        });

        player.sendEnterRoom("\u0003[Editeur] " + player.getPlayerName(), "");
    }
}