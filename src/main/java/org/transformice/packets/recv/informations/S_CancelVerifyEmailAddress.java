package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_SendEmailAddressCode;

@SuppressWarnings("unused")
public final class S_CancelVerifyEmailAddress implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.tmpEmailAddress = "";
        client.tmpEmailAddressCode = "";
        client.sendPacket(new C_SendEmailAddressCode(client.tmpEmailAddress, 0));
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 19;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}