package org.transformice.packets.recv.login;

// Imports
import jakarta.mail.MessagingException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSRandom;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.AccountErrorType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.login.PacketSendAccountLoginError;
import org.transformice.packets.send.login.PacketSendLoginQueuePosition;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_LOGIN_PLAYER)
public final class PacketRecvLogin extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        NetByteArray originalData = new NetByteArray(psData.toByteArray());
        psData = psData.decryptIdentification(gameServer.getConfig().getSWFConfig().getPacketKeys(), "identification");
        String nickname = psData.readUTF();
        String password = psData.readUTF();
        String swfUrl = psData.readUTF();
        String startRoom = psData.readUTF();
        int authKey = psData.readInt();
        int hardModeUnk = psData.readShort();
        int loginMethod = psData.readByte();
        for (int key : gameServer.getConfig().getSWFConfig().getLoginKeys())
            authKey ^= key;

        if (authKey != gameServer.getConfig().getSWFConfig().getAuthorizationKey())
            throw new PSReceivePacketException(String.format("[%s][PacketRecvLogin] Mismatch between the authorization key. %d != %d", client.getIpAddress(), authKey, gameServer.getConfig().getSWFConfig().getAuthorizationKey()));

        if (!gameServer.getConfig().getSWFConfig().getSwfUrl().isEmpty() && !gameServer.getConfig().getSWFConfig().getSwfUrl().equals(swfUrl))
            throw new PSReceivePacketException(String.format("[%s][PacketRecvLogin] Tried to log in with unofficial SWF %s.", client.getIpAddress(), swfUrl));

        if (hardModeUnk != 0x12 || loginMethod != 0x00)
            throw new PSReceivePacketException(String.format("[%s][PacketRecvLogin] Tried to log in unofficial method.", client.getIpAddress()));

        if (password.isEmpty()) {
            do {
                nickname = "*Souris_" + PSRandom.nextString(5);
            } while (gameServer.getPlayers().containsKey(nickname));
            try {
                if (!gameServer.getPlayerQueue().join(client, psHeader, originalData)) {
                    client.sendData(new PacketSendLoginQueuePosition(gameServer.getPlayerQueue().getQueuePosition(client)));
                    return;
                }

                client.setPlayer(new GamePlayer(client, true, new PlayerEntity(nickname, client.getIpAddress())));
                client.getPlayer().sendLogin(startRoom);
            } catch (Exception ex) {
                client.sendData(new PacketSendAccountLoginError(AccountErrorType.INTERNAL_ERROR, "", ""));
                throw new RuntimeException(ex);
            }
            return;
        }

        if (client.getLoginAttempts() >= gameServer.getConfig().getGameConfig().getLoginAttempts()) {
            gameServer.getTempBannedIPs().put(client.getIpAddress(), System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
            client.disconnect();
            return;
        }

        if (nickname.contains("@")) {
            if (!gameServer.getConfig().getGameConfig().isEnableEmailAddressLogin()) {
                client.setLoginAttempts(client.getLoginAttempts() + 1);
                client.sendData(new PacketSendAccountLoginError(AccountErrorType.WRONG_PASSWORD, "", ""));
                return;
            }

            List<PlayerEntity> accountList = gameServer.getDatabase().loadPlayersByEmail(nickname, password);
            if (accountList.isEmpty()) {
                client.setLoginAttempts(client.getLoginAttempts() + 1);
                client.sendData(new PacketSendAccountLoginError(AccountErrorType.WRONG_PASSWORD, "", ""));
                return;
            }

            client.sendData(new PacketSendAccountLoginError(AccountErrorType.PICK_ACCOUNTS_USING_EMAIL, accountList.stream().map(PlayerEntity::getUsername).collect(Collectors.joining("¤")), ""));
            return;
        }

        try {
            PlayerEntity entity = gameServer.getDatabase().loadPlayerByName(nickname);
            if (entity == null || !entity.password.equals(password)) {
                client.setLoginAttempts(client.getLoginAttempts() + 1);
                client.sendData(new PacketSendAccountLoginError(AccountErrorType.WRONG_PASSWORD, "", ""));
                return;
            }

            if (gameServer.getPlayers().containsKey(entity.getUsername())) {
                client.sendData(new PacketSendAccountLoginError(AccountErrorType.ALREADY_CONNECTED, "", ""));
                return;
            }

            if (client.getCheckHasConfirmed2FA() || client.tmp2FAcode.equals(psData.readUTF())) {
                client.tmp2FAcode = "";
                client.tmp2FAAdmin = "";
                entity.getAuthorizedIps().add(client.getIpAddress());
                client.setHasConfirmed2FA(false);
            }

            if (!entity.getStaffRoles().isEmpty() && !entity.getAuthorizedIps().contains(client.getIpAddress()) && !gameServer.getConfig().getSMTPConfig().getHost().isEmpty()) {
                boolean isImportant = entity.getStaffRoles().contains(StaffRoleType.ADMIN);
                if (!isImportant) {
                    String code = String.valueOf(PSRandom.nextLong(10000, 99999));
                    client.tmp2FAcode = code;
                    try {
                        gameServer.getPssmtp().send(entity.emailAddress, "Device verification", String.format("Your verification code is %s", code));
                    } catch (NullPointerException | MessagingException ignored) {
                        client.sendData(new PacketSendAccountLoginError(AccountErrorType.INTERNAL_ERROR, "", ""));
                    }
                } else {
                    client.tmp2FAAdmin = entity.getUsername();
                }

                client.sendData(new PacketSendAccountLoginError((isImportant ? AccountErrorType.DEVICE_AUTHORIZATION_REQUEST : AccountErrorType.DEVICE_AUTHORIZATION_REQUEST_NON_ADMIN), "", ""));
                return;
            }

            if (!gameServer.getPlayerQueue().join(client, psHeader, originalData)) {
                client.sendData(new PacketSendLoginQueuePosition(gameServer.getPlayerQueue().getQueuePosition(client)));
                return;
            }

            client.setPlayer(new GamePlayer(client, false, entity));
            client.getPlayer().sendLogin(startRoom);
        } catch (Exception ex) {
            client.sendData(new PacketSendAccountLoginError(AccountErrorType.INTERNAL_ERROR, "", ""));
            throw new RuntimeException(ex);
        }
    }
}