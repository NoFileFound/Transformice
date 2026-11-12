package org.transformice.packets.recv.informations;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.database.data.PlayerEntity;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendChangePasswordSuccessPopUp;
import org.transformice.packets.send.informations.PacketSendVerifyIdentityUsernameEmailAddress;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CHANGE_PASSWORD_REQUEST)
public final class PacketRecvChangePasswordRequest extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        String sha256 = psData.readUTF();
        String password = psData.readUTF();
        if (client.getPlayer() == null) {
            if (!client.tmpUsername.isEmpty() && client.isVerifiedIdentity) {
                PlayerEntity entity = gameServer.getDatabase().loadPlayerByName(client.tmpUsername);
                entity.password = password;
                gameServer.getDatabase().savePlayer(entity);
                client.tmpUsername = "";
                client.isVerifiedIdentity = false;
                client.sendData(new PacketSendVerifyIdentityUsernameEmailAddress(4));
                return;
            }

            return;
        }

        if (client.getPlayer().getIsGuest())
            return;

        client.getPlayer().getPlayerData().password = password;
        client.getPlayer().broadcastPacket(new PacketSendChangePasswordSuccessPopUp());
    }
}