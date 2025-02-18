package org.transformice.packets.recv.login;

// Imports
import java.util.concurrent.TimeUnit;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Account;
import org.transformice.database.collections.BetaInvite;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.login.C_AccountError;

@SuppressWarnings("unused")
public final class S_RegisterAccount implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.createAccountTimer.getRemainingTime() <= 0) {
            data.decryptPacket(Application.getSwfInfo().packet_keys, fingerPrint);
            String nickname = data.readString();
            String password_hash = data.readString();
            String email = data.readString();
            String captcha = data.readString();
            String betaKey = data.readString();
            String swfUrl = data.readString();

            if(!Application.getSwfInfo().swf_url.isEmpty() && !Application.getSwfInfo().swf_url.equals(swfUrl)) {
                // xor failure.
                Application.getLogger().info(String.format("[Connection] The IP address %s tried to login or register with unofficial swf.", client.getIpAddress()));
                client.closeConnection();
                return;
            }

            if(!nickname.matches("^(?=^(?:(?!.*_$).)*$)(?=^(?:(?!_{2,}).)*$)[A-Za-z][A-Za-z0-9_]{2,11}$") || nickname.length() > 11) {
                // invalid nickname.
                client.sendPacket(new C_AccountError(4));
                return;
            }

            if(!captcha.equals(client.registerCaptcha)) {
                // wrong captcha
                client.sendPacket(new C_AccountError(7));
                return;
            }

            String betaInviter = "";
            if(!betaKey.isEmpty()) {
                BetaInvite info = DBUtils.findBetaInviteByCode(betaKey);
                if(info == null) {
                    // invalid beta key.
                    client.sendPacket(new C_AccountError(8));
                    return;
                }
                betaInviter = info.getIssuer();
            }

            if(Application.getPropertiesInfo().use_tag_system) {
                nickname = nickname + "#" + SrcRandom.generateNumber(4);
            }

            Account account = DBUtils.findAccountByNickname(nickname);
            if(account != null) {
                // account already exist.
                client.sendPacket(new C_AccountError(3));
                return;
            }

            if(DBUtils.findAccountsByEmail(email, "").size() > 7) {
                // too many accounts in an email address.
                client.sendPacket(new C_AccountError(10));
                return;
            }

            Account instance = new Account(nickname, email, password_hash, betaInviter);
            instance.save();
            client.createAccountTimer.schedule(() -> {}, TimeUnit.MINUTES);
            client.sendLogin(instance, nickname, String.format("%c[Tutorial] %s", (char)3, nickname), true);
        } else {
            client.sendPacket(new C_AccountError(5));
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 7;
    }
}