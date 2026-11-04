package org.transformice.packets.recv.informations;

// Imports
import jakarta.mail.MessagingException;
import java.util.regex.Pattern;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSRandom;
import org.transformice.GameServer;
import org.transformice.database.data.PlayerEntity;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendVerifyIdentityUsernameEmailAddress;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_VERIFY_IDENTITY_USERNAME_EMAIL_ADDRESS_REQUEST)
public final class PacketRecvVerifyIdentityUsernameEmailAddressRequest extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        if (gameServer.getConfig().getSMTPConfig().getHost().isEmpty() || client.getPlayer() != null)
            return;

        String playerName = psData.readUTF();
        String emailAddress = psData.readUTF();
        if (!EMAIL_PATTERN.matcher(emailAddress).matches()) {
            client.sendData(new PacketSendVerifyIdentityUsernameEmailAddress(0));
            return;
        }

        PlayerEntity myEntity = gameServer.getDatabase().loadPlayerByName(playerName);
        if (myEntity == null || !myEntity.emailAddress.equals(emailAddress)) {
            client.sendData(new PacketSendVerifyIdentityUsernameEmailAddress(0));
            return;
        }

        client.tmpEmailAddress = emailAddress;
        client.tmpEmailAddressVerCode = PSRandom.nextString(8);
        client.tmpUsername = playerName;
        client.isVerifiedIdentity = false;
        try {
            gameServer.getPssmtp().send(client.tmpEmailAddress, "Identity Verification", String.format("Your account verification code is %s", client.tmpEmailAddressVerCode));
            client.sendData(new PacketSendVerifyIdentityUsernameEmailAddress(1));
        } catch (MessagingException e) {
            client.tmpEmailAddress = "";
            client.tmpEmailAddressVerCode = "";
            client.sendData(new PacketSendVerifyIdentityUsernameEmailAddress(0));
        }
    }
}