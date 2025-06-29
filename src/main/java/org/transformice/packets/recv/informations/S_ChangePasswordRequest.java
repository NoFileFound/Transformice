package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.newpackets.C_ChangePasswordSuccessful;

@SuppressWarnings("unused")
public final class S_ChangePasswordRequest implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isGuest()) return;

        try {
            String oldPassword = data.readString();
            String newPassword = data.readString();

            client.getAccount().setPassword(newPassword);
            client.sendPacket(new C_ChangePasswordSuccessful());
        } catch (Exception ignored) {

        }
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 42;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}