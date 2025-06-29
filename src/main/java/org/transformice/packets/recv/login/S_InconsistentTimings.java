package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_InconsistentTimings implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getServer().sendServerMessage(String.format("[AntiCheat] The player <N>%s</N> has abnormal speed.", client.getPlayerName()), false, null);
        client.closeConnection();
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 29;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}