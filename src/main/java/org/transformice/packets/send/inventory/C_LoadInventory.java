package org.transformice.packets.send.inventory;

// Imports
import java.util.List;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;
import org.transformice.properties.configs.InventoryConfig;

public final class C_LoadInventory implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LoadInventory(Map<String, Integer> playerInv, List<Integer> equippedConsumables) {
        this.byteArray.writeShort((short) playerInv.size());
        for(Map.Entry<String, Integer> entry : playerInv.entrySet()) {
            String consumable = entry.getKey();
            int consumableId, consumableImageId;
            if(consumable.contains("_")) {
                String[] split = consumable.split("_");
                consumableId = Short.parseShort(split[0]);
                consumableImageId = Integer.parseInt(split[1]);
            } else {
                consumableId = Short.parseShort(consumable);
                consumableImageId = -1;
            }

            InventoryConfig.ConsumableInfo info = Application.getInventoryInfo().get(consumableId);
            if(info == null) continue;

            this.byteArray.writeShort((short)consumableId);
            this.byteArray.writeUnsignedShort(entry.getValue());
            this.byteArray.writeUnsignedShort(info.sort);
            this.byteArray.writeUnsignedByte(info.priority);
            this.byteArray.writeBoolean(info.fromEvent);
            this.byteArray.writeBoolean(info.canUse);
            this.byteArray.writeBoolean(info.canEquip);
            this.byteArray.writeBoolean(info.canTrade);
            this.byteArray.writeByte(info.category);
            this.byteArray.writeByte(info.countdown);
            this.byteArray.writeBoolean(info.canUseWhenDead);
            this.byteArray.writeBoolean(consumableImageId != -1);
            if(consumableImageId != -1) {
                this.byteArray.writeString(info.images.split(";")[consumableImageId]);
            }

            if(equippedConsumables.contains(consumableId)) {
                this.byteArray.writeByte((equippedConsumables.indexOf(consumableId) + 1));
            }
            else {
                this.byteArray.writeByte(0);
            }
        }
    }

    @Override
    public int getC() {
        return 31;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}