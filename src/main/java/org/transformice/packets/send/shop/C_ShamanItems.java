package org.transformice.packets.send.shop;

// Imports
import java.util.Arrays;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanItems implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanItems(Map<Integer, String> shamanItems, String shamanLook) {
        this.byteArray.writeShort((short)shamanItems.size());
        for(var entry : shamanItems.entrySet()) {
            this.byteArray.writeShort(entry.getKey().shortValue());
            this.byteArray.writeBoolean(Arrays.stream(shamanLook.split(",")).anyMatch(e -> e.trim().equals(String.valueOf(entry.getKey()))));
            if(entry.getValue().contains("_")) {
                String[] itemSplitted = entry.getValue().split("_");
                String custom = (itemSplitted.length >= 2) ? itemSplitted[1] : "";
                String[] realCustom = custom.isEmpty() ? new String[0] : custom.split("\\+");
                this.byteArray.writeByte(realCustom.length + 1);
                for (String customPart : realCustom) {
                    this.byteArray.writeInt(Integer.parseInt(customPart, 16));
                }
            } else {
                this.byteArray.writeByte(0);
            }
        }
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 27;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}