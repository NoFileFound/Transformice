package org.transformice.packets.recv.tribe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@SuppressWarnings("unused")
public final class S_TribeHouseInvitationAnswer implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        Client inviter = client.getServer().getPlayers().get(data.readString());
        if(inviter == null) {
            client.sendPacket(new C_TranslationMessage("", "$InvTribu_MaisonVide"));
            return;
        }

        if(client.getInvitedTribeHouses().contains(inviter.getAccount().getTribeName())) {
            client.sendEnterRoom("*" + (char)3 + inviter.getAccount().getTribeName(), "");
        }
    }

    @Override
    public int getC() {
        return 16;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}