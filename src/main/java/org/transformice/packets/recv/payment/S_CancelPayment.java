package org.transformice.packets.recv.payment;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;

@SuppressWarnings("unused")
public final class S_CancelPayment implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendPacket(new C_ServerMessage(true, "In order this project to exist a payment system is never gonna get implemented. Thanks for understanding!"));
    }

    @Override
    public int getC() {
        return 12;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}