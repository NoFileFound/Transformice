package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_CanSendGift;

@SuppressWarnings("unused")
public final class S_CanSendGift implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendPacket(new C_CanSendGift(!client.isGuest()));
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 16;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}