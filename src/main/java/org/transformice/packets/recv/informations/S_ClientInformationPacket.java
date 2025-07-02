package org.transformice.packets.recv.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_ClientInformationPacket implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        data.readString();// http info
        String playerType = data.readString();
        data.readString(); // browser info
        String parentLoaderUrl = data.readString();
        data.readString(); // desktop

        if(!client.playerType.equals(playerType)) {
            client.getServer().getTempBlackList().add(client.getIpAddress());
            client.closeConnection();
            return;
        }

        if(!Application.getSwfInfo().swf_url.isEmpty() && !Application.getSwfInfo().swf_url.equals(parentLoaderUrl)) {
            client.getServer().getTempBlackList().add(client.getIpAddress());
            client.closeConnection();
            return;
        }
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 50;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}