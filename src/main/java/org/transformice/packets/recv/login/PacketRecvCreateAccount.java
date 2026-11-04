package org.transformice.packets.recv.login;

// Imports
import java.util.Set;
import java.util.regex.Pattern;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSRandom;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.AccountErrorType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.login.PacketSendAccountLoginError;
import org.transformice.packets.send.login.PacketSendLoginQueuePosition;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CREATE_ACCOUNT)
public final class PacketRecvCreateAccount extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    private final Pattern USERNAME_PATTERN = Pattern.compile("^(?!.*__)[A-Za-z][A-Za-z0-9_]{1,9}[A-Za-z0-9]$");
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final Set<String> STAFF_TAGS = Set.of("0001", "0010", "0015", "0020", "0095");

    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        psData.decryptPacket(gameServer.getConfig().getSWFConfig().getPacketKeys(), psHeader.readByte());
        String nickname = psData.readUTF();
        String password_hash = psData.readUTF();
        String email = psData.readUTF();
        String captcha = psData.readUTF();
        String betaKey = psData.readUTF();
        String swfUrl = psData.readUTF();
        if (!gameServer.getConfig().getSWFConfig().getSwfUrl().isEmpty() && !gameServer.getConfig().getSWFConfig().getSwfUrl().equals(swfUrl))
            throw new PSReceivePacketException(String.format("[%s][PacketRecvCreateAccount] Tried to log in with unofficial SWF %s.", client.getIpAddress(), swfUrl));

        if (!USERNAME_PATTERN.matcher(nickname).matches()  || password_hash.isEmpty()) {
            client.sendData(new PacketSendAccountLoginError(AccountErrorType.INVALID_CREDENTIALS, "", ""));
            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            client.sendData(new PacketSendAccountLoginError(AccountErrorType.INVALID_CREDENTIALS, "", ""));
            return;
        }

        if (!captcha.equals(client.getRegisterCaptchaCode())) {
            client.sendData(new PacketSendAccountLoginError(AccountErrorType.INVALID_CAPTCHA, "", ""));
            return;
        }

        if (gameServer.getAccountCreationTimer(client.getIpAddress()).getRemainingTime() > 0) {
            client.sendData(new PacketSendAccountLoginError(AccountErrorType.ACCOUNT_CREATION_COOLDOWN, "", ""));
            return;
        }

        if (gameServer.getDatabase().loadPlayersByEmail(email, "").size() > gameServer.getConfig().getGameConfig().getMaxAccountsPerEmail()) {
            client.sendData(new PacketSendAccountLoginError(AccountErrorType.EMAIL_ACCOUNT_LIMIT_REACHED, "", ""));
            return;
        }

        String tag;
        do {
            tag = String.format("%04d", PSRandom.nextInt(0, 9999));
        } while (STAFF_TAGS.contains(tag));
        nickname += "#" + tag;

        if (gameServer.getDatabase().loadPlayerByName(nickname) != null) {
            client.sendData(new PacketSendAccountLoginError(AccountErrorType.USERNAME_TAKEN, "", ""));
            return;
        }

        try {
            String roomName = String.format("%c[Tutorial] %s", (char)3, nickname);
            var playerData = new PlayerEntity(gameServer.getNextDatabaseCounterId("accountId"), nickname, password_hash, email, client.getIpAddress());
            gameServer.getDatabase().savePlayer(playerData);
            gameServer.getAccountCreationTimer(client.getIpAddress()).schedule();
            long authKey = gameServer.getConfig().getSWFConfig().getAuthorizationKey();
            for (int loginKey : gameServer.getConfig().getSWFConfig().getLoginKeys())
                authKey ^= loginKey;

            NetByteArray payload = new NetByteArray();
            payload.writeUTF(nickname, true);
            payload.writeUTF(password_hash, true);
            payload.writeUTF(swfUrl, true);
            payload.writeUTF(roomName, true);
            payload.writeInt(authKey);
            payload.writeShort((short) 0x12);
            payload.writeByte(0);
            payload.writeUTF("", true);
            payload = payload.encryptIdentification(gameServer.getConfig().getSWFConfig().getPacketKeys(), "identification");
            if (!gameServer.getPlayerQueue().join(client, psHeader, payload)) {
                client.sendData(new PacketSendLoginQueuePosition(gameServer.getPlayerQueue().getQueuePosition(client)));
                return;
            }

            client.setPlayer(new GamePlayer(client, false, playerData));
            client.getPlayer().sendLogin(roomName);
        } catch (Exception ex) {
            client.sendData(new PacketSendAccountLoginError(AccountErrorType.INTERNAL_ERROR, "", ""));
            throw new RuntimeException(ex);
        }
    }
}