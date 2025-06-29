package org.transformice.packets.send.shop;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShopMouseLook implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShopMouseLook(String playerLook, Integer mouseColor) {
        String[] look = playerLook.split(";");

        this.byteArray.writeUnsignedShort(Integer.parseInt(look[0]));
        for (String item : look[1].split(",")) {
            if (item.contains("_")) {
                String[] parts = item.split("_");
                String custom = (parts.length >= 2) ? parts[1] : "";
                String[] realCustom = custom.isEmpty() ? new String[0] : custom.split("\\+");

                this.byteArray.writeInt(Integer.parseInt(parts[0]));
                this.byteArray.writeByte(realCustom.length);
                for (String customPart : realCustom) {
                    this.byteArray.writeInt(Integer.parseInt(customPart, 16));
                }
            } else {
                this.byteArray.writeInt(Integer.parseInt(item));
                this.byteArray.writeByte(0);
            }
        }
        this.byteArray.writeInt(mouseColor);
    }

    @Override
    public int getC() {
        return 20;
    }

    @Override
    public int getCC() {
        return 17;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}