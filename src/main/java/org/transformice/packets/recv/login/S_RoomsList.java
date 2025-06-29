package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.login.C_RoomsList;

@SuppressWarnings("unused")
public final class S_RoomsList implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendPacket(new C_RoomsList(client, data.readByte()));
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 35;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}