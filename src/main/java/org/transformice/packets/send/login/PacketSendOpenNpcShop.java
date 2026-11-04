package org.transformice.packets.send.login;

// Imports
import java.util.List;
import java.util.Map;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.GamePlayer;
import org.transformice.config.VillageNPCConfig;
import org.transformice.config.inventory.ConsumableConfig;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendOpenNpcShop extends PSSendPacket {
    private final String npcName;
    private final List<VillageNPCConfig.Item> npcShopInfos;
    private final GamePlayer player;
    private final Map<Integer, ConsumableConfig> inventoryConfig;

    /**
     * Creates a new packet of SEND_OPEN_NPC_SHOP.
     * @param npcName The NPC's name.
     * @param npcShopInfos The NPC's shop.
     */
    public PacketSendOpenNpcShop(String npcName, List<VillageNPCConfig.Item> npcShopInfos, GamePlayer player, Map<Integer, ConsumableConfig> inventoryConfig) {
        super(PacketOpcodes.SEND_OPEN_NPC_SHOP);
        this.npcName = npcName;
        this.npcShopInfos = npcShopInfos;
        this.player = player;
        this.inventoryConfig = inventoryConfig;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(this.npcName, true);
        packet.writeUnsignedByte(this.npcShopInfos.size());
        for (var shopInfo : this.npcShopInfos) {
            packet.writeUnsignedByte(this.player.getParseInventory().isOwnItem(shopInfo.getType(), shopInfo.getItemId(), shopInfo.getCostQuantity(), this.inventoryConfig.get(shopInfo.getItemId())));
            packet.writeUnsignedByte(shopInfo.getType());
            packet.writeInt(shopInfo.getItemId());
            packet.writeShort((short) shopInfo.getQuantity());
            packet.writeUnsignedByte(shopInfo.getCostType());
            packet.writeInt(shopInfo.getCostId());
            packet.writeShort((short) shopInfo.getCostQuantity());
            packet.writeUTF(shopInfo.getHoverTextTemplate(), true);
            packet.writeUTF(shopInfo.getHoverTextArgs(), true);
        }

        return packet;
    }
}