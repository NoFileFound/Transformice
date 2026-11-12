package org.transformice.packets.recv.informations;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendVerificationSuccessfull;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CAN_SEND_SHOP_GIFT)
public final class PacketRecvCanSendShopGift extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        if (client.getPlayer().getIsGuest())
            return;

        client.getPlayer().broadcastPacket(new PacketSendVerificationSuccessfull(client.getPlayer().getPlayerData().isVerifiedEmailAddress || gameServer.getConfig().getSMTPConfig().getHost().isEmpty()));
    }
}