package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_Antitriche implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        short info = data.readShort();
        switch(info) {
            case 62: {
                client.getServer().sendServerMessage(String.format("[AntiCheat] The player <N>%s</N> is using Pgiex TFM/HileBol.", client.getPlayerName()), false, null);
                client.closeConnection();
                break;
            }
            case 60:
            case 64: {
                break;
            }
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 28;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}