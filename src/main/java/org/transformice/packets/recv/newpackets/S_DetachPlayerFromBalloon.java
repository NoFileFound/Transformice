package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.newpackets.C_DetachPlayerFromBalloon;

@SuppressWarnings("unused")
public final class S_DetachPlayerFromBalloon implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getRoom().sendAll(new C_DetachPlayerFromBalloon(client.getSessionId()));
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 11;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}