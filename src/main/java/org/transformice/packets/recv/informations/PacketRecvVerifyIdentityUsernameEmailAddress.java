package org.transformice.packets.recv.informations;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendVerifyIdentityUsernameEmailAddress;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_VERIFY_IDENTITY_USERNAME_EMAIL_ADDRESS)
public final class PacketRecvVerifyIdentityUsernameEmailAddress extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        GamePlayer player = client.getPlayer();
        if (player != null || gameServer.getConfig().getSMTPConfig().getHost().isEmpty() || client.tmpEmailAddress.isEmpty())
            return;

        String verCode = psData.readUTF();
        if (!client.tmpEmailAddressVerCode.equals(verCode)) {
            client.sendData(new PacketSendVerifyIdentityUsernameEmailAddress(0));
            return;
        }

        client.tmpEmailAddress = "";
        client.tmpEmailAddressVerCode = "";
        client.isVerifiedIdentity = true;
        client.sendData(new PacketSendVerifyIdentityUsernameEmailAddress(2));
    }
}