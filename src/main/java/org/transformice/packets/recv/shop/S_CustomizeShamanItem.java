package org.transformice.packets.recv.shop;

// Imports
import java.util.ArrayList;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_CustomizeShamanItem implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        short item_id = data.readShort();
        int length = data.readByte();

        ArrayList<Integer> arrayList = new ArrayList<>(length);
        for(int i = 0; i < length; i++){
            arrayList.add(data.readInt());
        }

        client.getParseShopInstance().customizeShamanItem(item_id, arrayList);
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 26;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}