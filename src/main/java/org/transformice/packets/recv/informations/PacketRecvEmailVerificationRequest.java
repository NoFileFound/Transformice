package org.transformice.packets.recv.informations;

// Imports
import java.util.regex.Pattern;
import jakarta.mail.MessagingException;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSRandom;
import org.transformice.GameServer;
import org.transformice.data.objects.EmailAddressRequestType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendEmailAddressVerifyStatus;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_EMAIL_VERIFICATION_REQUEST)
public final class PacketRecvEmailVerificationRequest extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        if (client.getPlayer() == null || client.getPlayer().getIsGuest() || client.getPlayer().getPlayerData().isVerifiedEmailAddress || gameServer.getConfig().getSMTPConfig().getHost().isEmpty())
            return;

        String emailAddress = psData.readUTF();
        if (!EMAIL_PATTERN.matcher(emailAddress).matches()) {
            client.sendData(new PacketSendEmailAddressVerifyStatus(EmailAddressRequestType.WRONG_INFORMATION, ""));
            return;
        }

        client.tmpEmailAddress = emailAddress;
        client.tmpEmailAddressVerCode = PSRandom.nextString(8);
        client.isVerifiedIdentity = false;
        gameServer.getLogger().debug(String.format("[EmailHandler] The player %s has requested email verification. [%s -> %s]", client.getPlayer().getPlayerName(), emailAddress, client.tmpEmailAddressVerCode));
        try {
            gameServer.getPssmtp().send(client.tmpEmailAddress, "Email Verification", String.format("Your account verification code is %s", client.tmpEmailAddressVerCode));
            client.sendData(new PacketSendEmailAddressVerifyStatus(EmailAddressRequestType.SEND_VERIFY_EMAIL_ADDRESS, client.tmpEmailAddress));
        } catch (MessagingException e) {
            client.tmpEmailAddress = "";
            client.tmpEmailAddressVerCode = "";
            client.sendData(new PacketSendEmailAddressVerifyStatus(EmailAddressRequestType.INTERNAL_ERROR, ""));
        }
    }
}