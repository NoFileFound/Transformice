package org.transformice.packets.send.player;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.config.shop.ShopConfig;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendOpenShopInterface extends PSSendPacket {
    private final Map<Integer, ShopConfig.ShopBannerConfig> shopBanners;
    private final Map<Integer, ShopConfig.ShopEmojiConfig> shopEmoji;
    private final Map<String, ShopConfig.ShopItemConfig> shopItems;
    private final Map<Integer, ShopConfig.ShopShamanItemConfig> shopShamanItems;
    private final List<Integer> playerAdventureBanners;
    private final List<String> playerClothes;
    private final List<Integer> playerEmoji;
    private final Set<Integer> playerFavoriteItems;
    private final Map<Integer, String> playerItems;
    private final Map<Integer, String> playerShamanItems;
    private final List<GameConfig.ShopOutfit> shopOutfits;
    private final int playerCheeses;
    private final int playerStrawberries;
    private final String playerLook;
    private final String shamanLook;
    private final int playerCurrentAdventureBanner;

    /**
     * Creates a new send packet of SEND_OPEN_SHOP_INTERFACE.
     * @param shopConfig The server's shop config.
     * @param isReloading Should send the server config?
     * @param playerAdventureBanners The player's purchased adventure banners.
     * @param playerClothes The player's purchased clothes.
     * @param playerEmoji The player's purchased emoji.
     * @param playerFavoriteItems The player's favorite shop items.
     * @param playerItems The player's shop regular items.
     * @param playerShamanItems The player's shop shaman items.
     */
    public PacketSendOpenShopInterface(ShopConfig shopConfig, boolean isReloading, List<Integer> playerAdventureBanners, List<String> playerClothes, List<Integer> playerEmoji, Set<Integer> playerFavoriteItems, Map<Integer, String> playerItems, Map<Integer, String> playerShamanItems, List<GameConfig.ShopOutfit> shopOutfits, int playerCheeses, int playerStrawberries, String playerLook, String shamanLook, int currentAdventureBanner) {
        super(PacketOpcodes.SEND_OPEN_SHOP_INTERFACE);
        this.shopBanners = shopConfig.getShopBanners();
        this.shopEmoji = shopConfig.getShopEmoji();
        this.shopItems = (isReloading ? shopConfig.getShopItems() : new HashMap<>());
        this.shopShamanItems = (isReloading ? shopConfig.getShopShamanItems() : new HashMap<>());
        this.playerAdventureBanners = playerAdventureBanners;
        this.playerClothes = playerClothes;
        this.playerEmoji = playerEmoji;
        this.playerFavoriteItems = playerFavoriteItems;
        this.playerItems = playerItems;
        this.playerShamanItems = playerShamanItems;
        this.shopOutfits = (isReloading ? shopOutfits : new ArrayList<>());
        this.playerCheeses = playerCheeses;
        this.playerStrawberries = playerStrawberries;
        this.playerLook = playerLook;
        this.shamanLook = shamanLook;
        this.playerCurrentAdventureBanner = currentAdventureBanner;
    }

    @Override
    public PSByteArray build() throws PSSendPacketException {
        NetByteArray packet = new NetByteArray();
        packet.writeInt(this.playerCheeses);
        packet.writeInt(this.playerStrawberries);
        packet.writeUTF(this.playerLook, true);
        packet.writeInt128(this.playerItems.size());
        for (var entry : this.playerItems.entrySet()) {
            packet.writeInt128(entry.getKey());
            packet.writeBoolean(this.playerFavoriteItems.contains(entry.getKey()));
            if (entry.getValue().contains("_")) {
                String[] itemSplitted = entry.getValue().split("_");
                String custom = (itemSplitted.length >= 2) ? itemSplitted[1] : "";
                String[] realCustom = custom.isEmpty() ? new String[0] : custom.split("\\+");
                packet.writeInt128(realCustom.length);
                for (String customPart : realCustom)
                    packet.writeInt128(Integer.parseInt(customPart, 16));
            } else {
                packet.writeInt128(-1);
            }
        }

        packet.writeInt(this.shopItems.size());
        for(var entry : this.shopItems.entrySet()) {
            String[] part = entry.getKey().split("_");
            packet.writeUnsignedShort(Integer.parseInt(part[0]));
            packet.writeUnsignedShort(Integer.parseInt(part[1]));
            packet.writeByte(entry.getValue().getColorNum());
            packet.writeBoolean(entry.getValue().getIsNew());
            packet.writeByte(entry.getValue().getType());
            packet.writeInt(entry.getValue().getCheesePrice());
            packet.writeInt(entry.getValue().getStrawberryPrice());
            packet.writeBoolean(entry.getValue().getRequireAnotherItem() != -1);
            if (entry.getValue().getRequireAnotherItem() != -1)
                packet.writeInt(entry.getValue().getRequireAnotherItem());
        }

        packet.writeByte(this.shopOutfits.size());
        for (var entry : this.shopOutfits) {
            packet.writeUnsignedShort(entry.getOutfitId());
            packet.writeUTF(entry.getOutfitLook(), true);
            packet.writeByte(entry.getOutfitBackgroundId().ordinal());
        }

        packet.writeShort((short)this.playerClothes.size());
        for(var entry : this.playerClothes) {
            String[] parts = entry.split("/");
            packet.writeUTF(parts[1] + ";" + parts[2] + ";" + parts[3], true);
        }

        packet.writeShort((short)this.playerShamanItems.size());
        for(var entry : this.playerShamanItems.entrySet()) {
            packet.writeShort(entry.getKey().shortValue());
            packet.writeBoolean(Arrays.stream(this.shamanLook.split(",")).anyMatch(e -> e.trim().equals(String.valueOf(entry.getKey()))));
            if(entry.getValue().contains("_")) {
                String[] itemSplitted = entry.getValue().split("_");
                String custom = (itemSplitted.length >= 2) ? itemSplitted[1] : "";
                String[] realCustom = custom.isEmpty() ? new String[0] : custom.split("\\+");
                packet.writeByte(realCustom.length + 1);
                for (String customPart : realCustom) {
                    packet.writeInt(Integer.parseInt(customPart, 16));
                }
            } else {
                packet.writeByte(0);
            }
        }

        packet.writeShort((short)this.shopShamanItems.size());
        for(var entry : this.shopShamanItems.entrySet()) {
            packet.writeInt(entry.getKey());
            packet.writeByte(entry.getValue().getColorNum());
            packet.writeBoolean(entry.getValue().getIsNew());
            packet.writeByte(entry.getValue().getType());
            packet.writeInt(entry.getValue().getCheesePrice());
            packet.writeShort((short)entry.getValue().getStrawberryPrice());
        }

        packet.writeInt128(this.shopEmoji.size());
        for(var entry : this.shopEmoji.entrySet()) {
            packet.writeInt128(entry.getKey());
            packet.writeInt128(entry.getValue().getCheesePrice());
            packet.writeInt128(entry.getValue().getStrawberryPrice());
            packet.writeBoolean(entry.getValue().getIsNew());
        }

        packet.writeInt128(this.playerEmoji.size());
        for(var entry : this.playerEmoji)
            packet.writeInt128(entry);

        packet.writeInt128(this.shopBanners.size());
        for(var entry : this.shopBanners.entrySet()) {
            packet.writeInt128(entry.getKey());
            packet.writeInt128(entry.getValue().getCheesePrice());
            packet.writeInt128(entry.getValue().getStrawberryPrice());
            packet.writeBoolean(entry.getValue().getIsNew());
        }

        packet.writeInt128(this.playerAdventureBanners.size());
        for(var entry : this.playerAdventureBanners)
            packet.writeInt128(entry);

        packet.writeInt128(this.playerCurrentAdventureBanner);
        return packet;
    }
}