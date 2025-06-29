package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.C_Profile;

@SuppressWarnings("unused")
public final class S_ChangeShamanBadge implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int shamanBadge = data.readByte();
        if(!client.getAccount().getShamanBadges().contains(shamanBadge) && shamanBadge != 0) return;

        client.getAccount().setEquippedShamanBadge(shamanBadge);
        client.sendPacket(new C_Profile(client.getAccount(), true));
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 79;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}