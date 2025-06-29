package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_SendEmailAddressCodeToChangePassword;
import org.transformice.packets.send.informations.C_VerifiedEmailAddress;

@SuppressWarnings("unused")
public final class S_VerifyEmailAddressChangePassword implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isGuest() || client.getAccount().isVerifiedEmail()) return;

        String code = data.readString();
        if(!client.tmpEmailAddressCode.equals(code)) {
            client.sendPacket(new C_SendEmailAddressCodeToChangePassword(0));
            return;
        }

        client.tmpEmailAddressCode = "";
        client.getAccount().setVerifiedEmail(true);
        client.sendPacket(new C_VerifiedEmailAddress(client.tmpEmailAddress, true));
        client.sendPacket(new C_SendEmailAddressCodeToChangePassword(2));
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 41;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}