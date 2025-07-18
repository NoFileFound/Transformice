package org.transformice.packets.send.player;

// Imports
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;
import org.transformice.properties.configs.shop.*;

public final class C_ShopOpen implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShopOpen(int shopCheeses, int shopStrawberries, String look, String shamanLook, boolean sendShopItems, List<String> purchasedClothes, Map<Integer, String> purchasedShamanItems, List<Integer> purchasedEmojis, Map<Integer, String> purchasedItems, List<Integer> favoritedItems) {
        Map<String, ShopItemConfig.ShopItem> shopItems = sendShopItems ? Application.getShopItemInfo() : new HashMap<>();
        Map<Integer, ShopOutfitsConfig.ShopOutfit> shopOutfits = sendShopItems ? Application.getShopOutfitsInfo() : new HashMap<>();
        Map<Integer, ShopShamanItemConfig.ShopShamanItem> shopShamanItems = sendShopItems ? Application.getShopShamanItemInfo() : new HashMap<>();
        Map<Integer, ShopEmojisConfig.ShopEmoji> shopEmojis = Application.getShopEmojiInfo();

        this.byteArray.writeInt(shopCheeses);
        this.byteArray.writeInt(shopStrawberries);
        this.byteArray.writeString(look);
        this.byteArray.writeInt128(purchasedItems.size());
        for(var entry : purchasedItems.entrySet()) {
            this.byteArray.writeInt128(entry.getKey());
            this.byteArray.writeBoolean(favoritedItems.contains(entry.getKey()));
            if(entry.getValue().contains("_")) {
                String[] itemSplitted = entry.getValue().split("_");
                String custom = (itemSplitted.length >= 2) ? itemSplitted[1] : "";
                String[] realCustom = custom.isEmpty() ? new String[0] : custom.split("\\+");
                this.byteArray.writeInt128(realCustom.length);
                for (String customPart : realCustom) {
                    this.byteArray.writeInt128(Integer.parseInt(customPart, 16));
                }
            } else {
                this.byteArray.writeInt128(0);
            }
        }

        this.byteArray.writeInt(shopItems.size());
        for(var entry : shopItems.entrySet()) {
            String[] part = entry.getKey().split("_");
            this.byteArray.writeUnsignedShort(Integer.parseInt(part[0]));
            this.byteArray.writeUnsignedShort(Integer.parseInt(part[1]));
            this.byteArray.writeByte(entry.getValue().color_num);
            this.byteArray.writeBoolean(entry.getValue().is_new);
            this.byteArray.writeByte(entry.getValue().type);
            this.byteArray.writeInt(entry.getValue().cheese_price);
            this.byteArray.writeInt(entry.getValue().strawberry_price);
            this.byteArray.writeBoolean(entry.getValue().require_item != -1);
            if(entry.getValue().require_item != -1) {
                this.byteArray.writeInt(entry.getValue().require_item);
            }
        }

        this.byteArray.writeByte(shopOutfits.size());
        for(var entry : shopOutfits.entrySet()) {
            this.byteArray.writeUnsignedShort(entry.getKey());
            this.byteArray.writeString(entry.getValue().outfit_look);
            this.byteArray.writeByte(entry.getValue().outfit_bg);
        }

        this.byteArray.writeShort((short)purchasedClothes.size());
        for(var entry : purchasedClothes) {
            String[] parts = entry.split("/");
            this.byteArray.writeString(parts[1] + ";" + parts[2] + ";" + parts[3]);
        }

        this.byteArray.writeShort((short)purchasedShamanItems.size());
        for(var entry : purchasedShamanItems.entrySet()) {
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

        this.byteArray.writeShort((short)shopShamanItems.size());
        for(var entry : shopShamanItems.entrySet()) {
            this.byteArray.writeInt(entry.getKey());
            this.byteArray.writeByte(entry.getValue().color_num);
            this.byteArray.writeBoolean(entry.getValue().is_new);
            this.byteArray.writeByte(entry.getValue().type);
            this.byteArray.writeInt(entry.getValue().cheese_price);
            this.byteArray.writeShort((short)entry.getValue().strawberry_price);
        }

        this.byteArray.writeInt128(shopEmojis.size());
        for(var entry : shopEmojis.entrySet()) {
            this.byteArray.writeInt128(entry.getKey());
            this.byteArray.writeInt128(entry.getValue().cheese_price);
            this.byteArray.writeInt128(entry.getValue().strawberry_price);
            this.byteArray.writeBoolean(entry.getValue().is_new);
        }

        this.byteArray.writeInt128(purchasedEmojis.size());
        for(var entry : purchasedEmojis) {
            this.byteArray.writeInt128(entry);
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}