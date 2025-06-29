package org.transformice.packets.recv.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_MulodromePlay implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if (!client.getRoom().getRedTeam().isEmpty() || !client.getRoom().getBlueTeam().isEmpty()) {
            client.getRoom().initMulodrome();
        }
    }

    @Override
    public int getC() {
        return 30;
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