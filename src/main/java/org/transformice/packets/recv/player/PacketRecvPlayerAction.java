package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSRandom;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.PacketSendRockPaperScissorsPacket;
import org.transformice.packets.send.player.PacketSendPlayerAction;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_ACTION)
public final class PacketRecvPlayerAction extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int actionType = psData.readByte();
        int playerSessionId = psData.readInt();
        GamePlayer player = client.getPlayer();
        GameRoom room = client.getPlayer().getPlayerRoom();
        String flag = (psData.getLength() > 0 ? psData.readUTF() : client.getCountryCode());
        switch (actionType) {
            case 10: // flag
                room.broadcastPacket(new PacketSendPlayerAction(player.getSessionId(), 10, flag, false));
                break;
            case 14: // high five
            case 18: // hug
            case 22: // kissing
            case 26: // rock paper scissors.
                GamePlayer repPlayer = gameServer.getPlayerBySessionId(playerSessionId);
                room.broadcastPacket(player, new PacketSendPlayerAction(player.getSessionId(), actionType + 1, "", false));
                repPlayer.getPlayerRoom().broadcastPacket(repPlayer, new PacketSendPlayerAction(repPlayer.getSessionId(), actionType + 1, "", false));
                if (actionType == 26) {
                    int num1 = PSRandom.nextInt(0, 2);
                    int num2 = PSRandom.nextInt(0, 2);
                    room.broadcastPacket(new PacketSendRockPaperScissorsPacket(player.getSessionId(), num1, repPlayer.getSessionId(), num2));
                }
                break;
            default:
                room.broadcastPacket(player, new PacketSendPlayerAction(player.getSessionId(), actionType, "", false));
                break;
        }

        if (player.isShaman)
            player.getParseSkills().handleSkillByAction(actionType);

        gameServer.getParseEvents().handleEventPlayerEmote(room, player, actionType, playerSessionId);
        room.getParseLua().handleEventPlayerEmote(player, actionType);
    }
}