package org.transformice.packets.recv.informations;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.data.objects.EmailAddressRequestType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendEmailAddressVerifyStatus;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CANCEL_IDENTITY_VERIFICATION)
public final class PacketRecvEmailVerificationCancel extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        if (gameServer.getConfig().getSMTPConfig().getHost().isEmpty() || (client.getPlayer() != null && (client.getPlayer().getIsGuest() || client.getPlayer().getPlayerData().isVerifiedEmailAddress)))
            return;

        client.tmpEmailAddressVerCode = "";
        client.tmpEmailAddress = "";
        client.isVerifiedIdentity = false;
        client.sendData(new PacketSendEmailAddressVerifyStatus(EmailAddressRequestType.CANCEL_VERIFICATION, ""));
    }
}