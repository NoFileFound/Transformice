package org.transformice.packets.recv.modopwet;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_WatchReport implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isGuest()) {
            client.closeConnection();
            client.getServer().getTempBlackList().add(client.getIpAddress());
            return;
        }

        if(client.getAccount().getPrivLevel() >= 9) {
            client.getParseModopwetInstance().sendWatchReport(data.readString(), data.readBoolean());
        }
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 24;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}