package org.transformice.packets.send.login;

// Imports
import java.util.ArrayList;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.SendPacket;
import org.transformice.properties.configs.VillageNpcsConfig;

public final class C_OpenNPCShop implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OpenNPCShop(String npcName, ArrayList<VillageNpcsConfig.VillageNPCShopInfo> npcShopInfos, Client player) {
        this.byteArray.writeString(npcName);
        this.byteArray.writeUnsignedByte(npcShopInfos.size());
        for(var shopInfo : npcShopInfos) {
            this.byteArray.writeUnsignedByte(this.isOwnItem(shopInfo.item_id, shopInfo.type, player) ? 2 : (player.getAccount().getInventory().get(String.valueOf(shopInfo.cost_id)) == null || player.getAccount().getInventory().get(String.valueOf(shopInfo.cost_id)) < shopInfo.cost_quantity) ? 1 : 0);
            this.byteArray.writeUnsignedByte(shopInfo.type);
            this.byteArray.writeInt(shopInfo.item_id);
            this.byteArray.writeShort((short) shopInfo.quantity);
            this.byteArray.writeUnsignedByte(shopInfo.cost_type);
            this.byteArray.writeInt(shopInfo.cost_id);
            this.byteArray.writeShort((short) shopInfo.cost_quantity);
            this.byteArray.writeString(shopInfo.hover_text_template);
            this.byteArray.writeString(shopInfo.hover_text_args);
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 38;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }

    private boolean isOwnItem(int item_id, int item_type, Client client) {
        if(item_type == 1) {
            return client.getAccount().getShopBadges().containsKey(item_id);
        }

        if(item_type == 2) {
            return client.getAccount().getShamanBadges().contains(item_id);
        }

        if(item_type == 3) {
            return client.getAccount().getTitleList().contains(item_id + 0.1);
        }

        if(item_type == 4) {
            if(client.getAccount().getInventory().get(String.valueOf(item_id)) == null) return false;

            return client.getAccount().getInventory().get(String.valueOf(item_id)) > Application.getInventoryInfo().get(item_id).limit;
        }

        if(item_type == 5) {
            return client.getAccount().getShopItems().containsKey(item_id);
        }

        if(item_type == 7) {
            return client.getAccount().getPurchasedEmojis().contains(item_id);
        }

        return false;
    }
}