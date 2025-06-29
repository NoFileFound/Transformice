package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_ChooseNextShaman implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.getRoom().getRoomName().equals("*strm_" + client.getPlayerName())) {
            client.closeConnection();
            return;
        }

        client.getRoom().setForceNextShaman(client);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}