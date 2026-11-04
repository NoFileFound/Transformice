package org.transformice.packets.recv.room;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_ENTER_ROOM)
public final class PacketRecvEnterRoom extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        String community = psData.readUTF();
        String roomName = psData.readUTF();
        String password = psData.readUTF();
        boolean salonAuto = psData.readBoolean();
        GamePlayer player = client.getPlayer();
        if (roomName.isEmpty() || salonAuto) {
            player.sendEnterRoom(gameServer.getRecommendedRoom(roomName, player.getPlayerCommunity()), "");
            return;
        }

        if (psData.getLength() > 0) {
            String roomPassword = psData.readUTF();
            boolean withoutShamanSkills = psData.readBoolean();
            boolean withoutPhysicalConsumables = psData.readBoolean();
            boolean withoutAdventureMaps = psData.readBoolean();
            boolean withMiceCollisions = psData.readBoolean();
            boolean withFallDamage = psData.readBoolean();
            int roundDuration = psData.readUnsignedByte();
            int miceWeight = psData.readInt();
            int maximumPlayers = psData.readShort();
            int sz = psData.readUnsignedByte();
            List<Integer> mapRotation = new ArrayList<>();
            for (int i = 0; i < sz; i++) {
                int rotationId = psData.readByte();
                if (rotationId < 0 || rotationId > 11 && rotationId != 13 && rotationId != 17 && rotationId != 18 && rotationId != 19)
                    continue;

                mapRotation.add(rotationId);
            }

            player.sendEnterRoom(roomName, roomPassword, withoutShamanSkills, withoutPhysicalConsumables, withoutAdventureMaps, withMiceCollisions, withFallDamage, roundDuration, miceWeight, maximumPlayers, mapRotation);
        } else {
            player.sendEnterRoom(roomName, password);
        }
    }
}