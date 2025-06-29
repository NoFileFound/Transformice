package org.transformice.packets.recv.tribe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_EnterTribeHouse implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.getAccount().getTribeName().isEmpty()) {
            client.closeConnection();
            client.getServer().getTempBlackList().add(client.getIpAddress());
        }

        client.sendEnterRoom("*" + (char) 3 + client.getAccount().getTribeName(), "");
    }

    @Override
    public int getC() {
        return 16;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}