package org.transformice.packets.recv.inventory;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_UseConsumable implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getParseInventoryInstance().useConsumable(data.readShort());
    }

    @Override
    public int getC() {
        return 31;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}