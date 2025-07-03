package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.libraries.JakartaMail;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_SendEmailAddressCode;

@SuppressWarnings("unused")
@Deprecated()
public final class S_VerifyEmailAddressRequestWinExclusivePrize implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isGuest() || client.getAccount().isVerifiedEmail()) return;

        String emailAddress = data.readString();
        if(!emailAddress.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            client.sendPacket(new C_SendEmailAddressCode(emailAddress, 8));
            return;
        }

        client.tmpEmailAddress = emailAddress;
        client.tmpEmailAddressCode = SrcRandom.generateNumberAndLetters(7);

        boolean isSent = JakartaMail.sendMessage(client.tmpEmailAddress, "Verification", client.tmpEmailAddressCode);
        client.sendPacket(new C_SendEmailAddressCode(emailAddress, (isSent) ? 1 : 4));
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 64;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}