package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Account;
import org.transformice.libraries.JakartaMail;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_SendEmailAddressCode;

@SuppressWarnings("unused")
public final class S_VerifyAccountByNickAndEmail implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isGuest() || (client.getAccount().isVerifiedEmail())) return;

        String nickname = data.readString();
        String email = data.readString();
        Account myAccount = DBUtils.findAccountByNickname(nickname);
        if(myAccount == null || !myAccount.getEmailAddress().equals(email)) {
            client.sendPacket(new C_SendEmailAddressCode(email, 8));
            return;
        }

        client.tmpEmailAddress = email;
        client.tmpEmailAddressCode = SrcRandom.generateNumberAndLetters(7);

        boolean isSent = JakartaMail.sendMessage(client.tmpEmailAddress, "Verification", client.tmpEmailAddressCode);
        client.sendPacket(new C_SendEmailAddressCode(email, (isSent) ? 10 : 4));
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 40;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}