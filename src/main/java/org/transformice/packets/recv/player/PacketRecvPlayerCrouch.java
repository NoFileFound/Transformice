package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_CROUCH)
public final class PacketRecvPlayerCrouch extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int crouchId = psData.readByte();
        client.getPlayer().getPlayerRoom().broadcastPacket(new PSSendPacket(PacketOpcodes.SEND_PLAYER_CROUCH) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return new PSByteArray().writeInt(client.getPlayer().getSessionId()).writeByte(crouchId).writeBoolean(false);
            }
        });
    }
}