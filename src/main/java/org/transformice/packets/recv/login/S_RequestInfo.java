package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_RequestInfo;

@SuppressWarnings("unused")
public final class S_RequestInfo implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendPacket(new C_RequestInfo());
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 40;
    }
}