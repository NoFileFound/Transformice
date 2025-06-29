package org.transformice.packets.recv.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.sync.C_PlayerCrouch;

@SuppressWarnings("unused")
public final class S_PlayerCrouch implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getRoom().sendAll(new C_PlayerCrouch(client.getSessionId(), data.readByte()));
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}