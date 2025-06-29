package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_CodeValidatedEmailAddress;
import org.transformice.packets.send.informations.C_SendEmailAddressCode;
import org.transformice.packets.send.informations.C_SendEmailAddressCodeToChangePassword;
import org.transformice.packets.send.informations.C_VerifiedEmailAddress;

@SuppressWarnings("unused")
public final class S_VerifyEmailAddressAnswer implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isGuest() || (client.getAccount() != null && client.getAccount().isVerifiedEmail())) return;

        String code = data.readString();
        if(!code.equals(client.tmpEmailAddressCode)) {
            client.sendPacket(new C_CodeValidatedEmailAddress(false));
            if(client.tmpEmailAddress != null) {
                client.sendPacket(new C_SendEmailAddressCode(client.tmpEmailAddress, 3));
            } else {
                client.sendPacket(new C_SendEmailAddressCodeToChangePassword(3));
            }
            return;
        }

        client.sendPacket(new C_CodeValidatedEmailAddress(true));
        client.sendPacket(new C_VerifiedEmailAddress(client.tmpEmailAddress, true));

        client.getAccount().setEmailAddress(client.tmpEmailAddress);
        client.getAccount().setVerifiedEmail(true);
        client.tmpEmailAddress = "";
        client.tmpEmailAddressCode = "";
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 31;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}