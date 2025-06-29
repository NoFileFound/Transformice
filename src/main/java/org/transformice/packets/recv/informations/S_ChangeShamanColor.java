package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_ChangeShamanColor implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getAccount().setShamanColor(data.readInt());
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 18;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}