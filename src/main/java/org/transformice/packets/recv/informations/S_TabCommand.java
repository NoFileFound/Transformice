package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_TabCommand implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getServer().getCommandHandler().invokeCommand(client, data.readString(), true);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 48;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}