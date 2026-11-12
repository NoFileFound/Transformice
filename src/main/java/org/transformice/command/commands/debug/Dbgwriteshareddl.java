package org.transformice.command.commands.debug;

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
import org.transformice.packets.PacketOpcodes;

@Command(name = "dbgwriteshareddl", usage = "[String]", description = "WriteSharedObjectDl in the swf.", permission = Command.CommandPermission.DEBUG_ONLY, requiredArgs = 1)
@SuppressWarnings("unused")
public final class Dbgwriteshareddl implements CommandHandler {
    @Override
    public void execute(GamePlayer player, GameServer server, List<String> args) {
        for (GamePlayer client : server.getPlayers().values()) {
            client.broadcastPacket(new PSSendPacket(PacketOpcodes.SEND_WRITE_SHARED_OBJECT_DL) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    return new NetByteArray().writeUTF(args.getFirst(), true);
                }
            });
        }

        CommandHandler.sendServerMessage(player, "OK");
    }
}