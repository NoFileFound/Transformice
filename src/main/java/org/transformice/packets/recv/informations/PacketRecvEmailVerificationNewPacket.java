package org.transformice.packets.recv.informations;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.EmailAddressRequestType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendEmailAddressVerified;
import org.transformice.packets.send.informations.PacketSendEmailAddressVerifyStatus;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_EMAIL_VERIFICATION_NEW_PACKET)
public final class PacketRecvEmailVerificationNewPacket extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        if (player == null || player.getIsGuest() || player.getPlayerData().isVerifiedEmailAddress || gameServer.getConfig().getSMTPConfig().getHost().isEmpty() || client.tmpEmailAddress.isEmpty() || client.tmpEmailAddressVerCode.isEmpty()) {
            client.sendData(new PacketSendEmailAddressVerifyStatus(EmailAddressRequestType.INTERNAL_ERROR, client.tmpEmailAddress));
            return;
        }

        String verCode = psData.readUTF();
        if (!client.tmpEmailAddressVerCode.equals(verCode)) {
            player.broadcastPacket(new PacketSendEmailAddressVerifyStatus(EmailAddressRequestType.WRONG_INFORMATION, client.tmpEmailAddress));
            return;
        }

        player.getPlayerData().emailAddress = client.tmpEmailAddress;
        player.getPlayerData().isVerifiedEmailAddress = true;
        client.tmpEmailAddressVerCode = "";
        client.isVerifiedIdentity = true;
        gameServer.getLogger().debug(String.format("[EmailHandler] The player %s has verified their email address. [%s]", client.getPlayer().getPlayerName(), client.getPlayer().getPlayerData().emailAddress));
        player.broadcastPacket(new PacketSendEmailAddressVerifyStatus(EmailAddressRequestType.VERIFICATION_SUCCESSFULL, ""));
        player.broadcastPacket(new PacketSendEmailAddressVerified(player.getPlayerData().emailAddress, true));
        client.isVerifiedIdentity = false;
    }
}