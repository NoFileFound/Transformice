package org.transformice.packets.recv.room;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.data.objects.RoomType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendTotemItemCount;
import org.transformice.packets.send.player.PacketSendShamanDoneInvocationSound;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_SHAMAN_SPAWN_OBJECT)
public final class PacketRecvShamanSpawnObject extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        GameRoom room = player.getPlayerRoom();
        int roundCode = psData.readByte();
        if (roundCode == room.getLastRoundId()) {
            int objectId = psData.readInt128();
            int shamanObjectId = psData.readInt128();
            int posX = psData.readInt128();
            int posY = psData.readInt128();
            int angle = psData.readInt128();
            int velX = psData.readInt128();
            int velY = psData.readInt128();
            boolean miceCollidable = psData.readBoolean();
            boolean spawnedByPlayer = psData.readBoolean();
            int shamanSessionId = psData.readInt128();
            if ((!player.isShaman || shamanSessionId != player.getSessionId()) && spawnedByPlayer)
                return;

            if (spawnedByPlayer) {
                player.broadcastPacket(new PacketSendShamanDoneInvocationSound(shamanObjectId));
                if (room.getRoomType() == RoomType.TOTEM) {
                    if ((int) player.tempTotem[0] < 20) {
                        player.tempTotem[0] = ((int) player.tempTotem[0]) + 1;
                        player.broadcastPacket(new PacketSendTotemItemCount((int) player.tempTotem[0]));
                        player.tempTotem[1] += "#2#" + String.join("\u0001", String.valueOf(shamanObjectId), String.valueOf(posX), String.valueOf(posY), String.valueOf(angle), String.valueOf(velX), String.valueOf(velY), String.valueOf(miceCollidable ? 1 : 0));
                    }
                } else {
                    player.getParseSkills().handleSkill(objectId, shamanObjectId, posX, posY, angle);
                }
            }

            room.sendAddShamanObject(objectId, shamanObjectId, posX, posY, angle, velX, velY, false, miceCollidable, player, false);
        }
    }
}

/// TODO: [Anticheat] Implement anticheat.