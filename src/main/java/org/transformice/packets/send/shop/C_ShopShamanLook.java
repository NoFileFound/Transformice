package org.transformice.packets.send.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShopShamanLook implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShopShamanLook(String shamanLook) {
        ByteArray items = new ByteArray();
        String[] shamanLookItems = shamanLook.split(",");
        short count = 0;

        for (String item : shamanLookItems) {
            short realItem;
            if (item.contains("_")) {
                realItem = Short.parseShort(item.split("_")[0]);
            } else {
                realItem = Short.parseShort(item);
            }

            if (realItem != 0) {
                items.writeShort(realItem);
                count++;
            }
        }

        this.byteArray.writeShort(count);
        this.byteArray.writeBytes(items.toByteArray());
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 24;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}