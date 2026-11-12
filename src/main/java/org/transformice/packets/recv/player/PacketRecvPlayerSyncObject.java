package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.GameRoom;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_SYNC_OBJECT)
public final class PacketRecvPlayerSyncObject extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int roundId = psData.readInt();
        GameRoom room = client.getPlayer().getPlayerRoom();
        if (roundId == room.getLastRoundId()) {
            NetByteArray packet = new NetByteArray();
            while (psData.getLength() > 0) {
                int objectId = psData.readInt();
                short objectCode = psData.readShort();
                packet.writeInt(objectId);
                packet.writeShort(objectCode);
                if (objectCode != -1) {
                    int posX = psData.readInt();
                    int posY = psData.readInt();
                    short velX = psData.readShort();
                    short velY = psData.readShort();
                    short objRotation = psData.readShort();
                    short objRotationSpeed = psData.readShort();
                    boolean isObjGhost = psData.readBoolean();
                    boolean isStationary = psData.readBoolean();
                    packet.writeInt(posX).writeInt(posY).writeShort(velX).writeShort(velY).writeShort(objRotation).writeShort(objRotationSpeed).writeBoolean(isObjGhost).writeBoolean(isStationary).writeBoolean(room.getAliveCount() > 0);
                }
            }

            room.broadcastPacket(client.getPlayer(), new PSSendPacket(PacketOpcodes.SEND_PLAYER_SYNC_OBJECT) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    return packet;
                }
            });
        }
    }
}