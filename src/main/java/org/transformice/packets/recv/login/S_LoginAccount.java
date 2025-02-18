package org.transformice.packets.recv.login;

// Imports
import java.util.List;
import java.util.stream.Collectors;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Account;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.login.C_AccountError;

@SuppressWarnings("unused")
public final class S_LoginAccount implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        data = data.decryptIdentification(Application.getSwfInfo().packet_keys, "identification");

        String nickname = data.readString();
        String password = data.readString();
        String swfUrl = data.readString();
        String startRoom = data.readString();
        int authKey = data.readInt();
        for (int i = 0; i < Application.getSwfInfo().login_keys.size(); i++) {
            authKey ^= Application.getSwfInfo().login_keys.get(i);
        }

        if ((!Application.getSwfInfo().swf_url.isEmpty() && !Application.getSwfInfo().swf_url.equals(swfUrl)) || Application.getSwfInfo().authorization_key != authKey) {
            Application.getLogger().info(String.format("[Connection] The IP address %s tried to login or register with unofficial swf.", client.getIpAddress()));
            client.closeConnection();
            return;
        }

        if(client.loginAttempts > Application.getPropertiesInfo().login_attempts) {
            Application.getLogger().warn(String.format("[DDOS] The ip %s has exceeded the maximum number of login attempts.", client.getIpAddress()));
            client.getServer().getTempBlackList().add(client.getIpAddress());
            client.closeConnection();
            return;
        }

        if(password.isEmpty()) {
            // guest login
            if(Application.getPropertiesInfo().beta_login) {
                client.sendPacket(new C_AccountError(2));
                return;
            }

            if(nickname.isEmpty()) {
                nickname = "*Souris_" + SrcRandom.generateNumber(5);
            } else {
                nickname = "*" + nickname.replaceAll("[^a-zA-Z0-9]", "");
            }

            client.sendLogin(null, nickname, String.format("%c[Tutorial] %s", (char)3, nickname), false);
            return;
        }

        if(nickname.contains("@") && Application.getPropertiesInfo().allow_email) {
            List<Account> accountList = DBUtils.findAccountsByEmail(nickname, password);
            if(accountList == null || accountList.isEmpty()) {
                client.loginAttempts++;
                client.sendPacket(new C_AccountError(2));
                return;

            } else if(accountList.size() == 1) {
                nickname = accountList.getFirst().getPlayerName();
            }
            else {
                client.sendPacket(new C_AccountError(accountList.stream().map(Account::getPlayerName).collect(Collectors.joining("¤"))));
                return;
            }
        }

        Account account = DBUtils.findAccountByPassword(nickname, password);
        if(account == null) {
            client.loginAttempts++;
            client.sendPacket(new C_AccountError(2));
            return;
        }

        if(client.getServer().checkIsConnected(nickname)) {
            client.loginAttempts++;
            client.sendPacket(new C_AccountError(1));
            return;
        }

        if(Application.getPropertiesInfo().beta_login) {
            if(account.getBetaInviter().isEmpty() && account.getPrivLevel() < 4) {
                client.loginAttempts++;
                client.sendPacket(new C_AccountError(2));
                return;
            }
        }

        client.sendLogin(account, nickname, startRoom, false);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 8;
    }
}