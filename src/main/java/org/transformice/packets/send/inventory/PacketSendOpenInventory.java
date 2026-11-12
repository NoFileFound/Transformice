package org.transformice.packets.send.inventory;

// Imports
import java.util.List;
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.config.inventory.ConsumableConfig;
import org.transformice.database.data.PlayerEntity;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendOpenInventory extends PSSendPacket {
    private final Map<PlayerEntity.InventoryItem, ConsumableConfig> playerInventory;
    private final List<Integer> equippedConsumables;

    /**
     * Creates a new packet of SEND_OPEN_INVENTORY.
     * @param playerInventory The player's inventory.
     */
    public PacketSendOpenInventory(Map<PlayerEntity.InventoryItem, ConsumableConfig> playerInventory, List<Integer> equippedConsumables) {
        super(PacketOpcodes.SEND_OPEN_INVENTORY);
        this.playerInventory = playerInventory;
        this.equippedConsumables = equippedConsumables;
    }

    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeShort((short) this.playerInventory.size());
        for (Map.Entry<PlayerEntity.InventoryItem, ConsumableConfig> entry : playerInventory.entrySet()) {
            packet.writeShort((short) entry.getKey().itemId);
            packet.writeShort((short) entry.getKey().itemAmount);
            packet.writeUnsignedShort(entry.getValue().getSort());
            packet.writeUnsignedByte(entry.getValue().getPriority());
            packet.writeBoolean(entry.getValue().getFromEvent());
            packet.writeBoolean(entry.getValue().getCanUse());
            packet.writeBoolean(entry.getValue().getCanEquip());
            packet.writeBoolean(entry.getValue().getCanTrade());
            packet.writeByte(entry.getValue().getCategory());
            packet.writeByte(entry.getValue().getCountdown());
            packet.writeBoolean(entry.getValue().getCanUseWhenDead());
            packet.writeBoolean(!entry.getKey().getItemImage().isEmpty());
            if (!entry.getKey().getItemImage().isEmpty())
                packet.writeUTF(entry.getKey().getItemImage(), true);

            if (this.equippedConsumables.contains(entry.getKey().itemId))
                packet.writeByte(this.equippedConsumables.indexOf(entry.getKey().itemId) + 1);
            else
                packet.writeByte(0);
        }

        return packet;
    }
}