package org.transformice.packets.recv.room;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.room.PacketSendVisualizeConsumable;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_VISUALIZE_CONSUMABLE)
public final class PacketRecvVisualizeConsumable extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int code = psData.readUnsignedByte();
        GamePlayer player = client.getPlayer();
        switch (code) {
            case 2:
                player.getPlayerRoom().broadcastPacket(player, new PacketSendVisualizeConsumable(player.getSessionId(), 2, new Object[]{player.drawColor, psData.readInt(), psData.readInt(), psData.readInt(), psData.readInt()}));
                break;
            case 3:
                if (player.hasStaffPermission(StaffRoleType.ARBITRE, false))
                    player.getPlayerRoom().broadcastPacket(new PacketSendVisualizeConsumable(player.getSessionId(), 3, new Object[]{psData.readUTF()}));

                break;
        }
    }
}