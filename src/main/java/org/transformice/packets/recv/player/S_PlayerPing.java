package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_PingPacket;

@SuppressWarnings("unused")
public final class S_PlayerPing implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendPacket(new C_PingPacket(data.readByte(), true));
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 30;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}