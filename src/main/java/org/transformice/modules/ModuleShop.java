package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.List;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.config.shop.ShopConfig;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.data.objects.MissionType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.modules.impl.ModuleServer;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.PacketSendServerMessage;
import org.transformice.packets.send.player.PacketSendPlayerAvailableEmoji;
import org.transformice.packets.send.shop.*;

public final class ModuleShop extends ModuleServer {
    /**
     * Creates a shop module.
     *
     * @param server The game server.
     */
    public ModuleShop(GameServer server) {
        super(server);
    }

    /**
     * Gets the colors of the given shaman object.
     * @param player The player's instance.
     * @param shamanItemId The shaman item's id.
     * @return The customization (colors) of it.
     */
    public byte[] getCustomShamanColors(GamePlayer player, int shamanItemId) {
        for (String item : player.getPlayerData().shamanLook.split(",")) {
            int separator = item.indexOf('_');
            if (separator == -1 || Integer.parseInt(item.substring(0, separator)) != shamanItemId)
                continue;

            String customs = item.substring(separator + 1);
            if (customs.isEmpty())
                return new byte[0];

            String[] colors = customs.split("\\+");
            byte[] result = new byte[colors.length * 4];
            for (int i = 0; i < colors.length; i++) {
                int color = Integer.parseInt(colors[i], 16);
                int offset = i * 4;
                result[offset] = (byte) (color >>> 24);
                result[offset + 1] = (byte) (color >>> 16);
                result[offset + 2] = (byte) (color >>> 8);
                result[offset + 3] = (byte) color;
            }

            return result;
        }

        return new byte[0];
    }

    /**
     * Handles when the player wants to add an outfit.
     * @param player The player's instance.
     * @param outfitName The outfit's name.
     * @param background The outfit's background.
     * @param timestamp The outfit's expiration date.
     * @param outfitLook The outfit's look.
     */
    public void handleAddOutfit(GamePlayer player, String outfitName, int background, String timestamp, String outfitLook) {
        try {
            GameConfig.ShopOutfit.OutfitBackground outfitBackground = GameConfig.ShopOutfit.OutfitBackground.parseFrom(background);
            if (outfitBackground == GameConfig.ShopOutfit.OutfitBackground.NONE) {
                player.broadcastPacket(new PacketSendServerMessage(true, "The background does not exist."));
                this.sendOpenA801OutfitsWindow(player);
                return;
            }

            long expirationTime = Long.parseLong(timestamp);
            if (expirationTime < Utils.getUnixTime()) {
                player.broadcastPacket(new PacketSendServerMessage(true, "Invalid Unix timestamp. Get one at https://www.unixtimestamp.com/."));
                this.sendOpenA801OutfitsWindow(player);
                return;
            }

            this.server.getConfig().getGameConfig().getShopOutfits().add(new GameConfig.ShopOutfit(this.server.getNextDatabaseCounterId("lastOutfitId"), outfitName, background, outfitLook, expirationTime));
        } catch (NumberFormatException e) {
            player.broadcastPacket(new PacketSendServerMessage(true, "Invalid Unix timestamp. Get one at https://www.unixtimestamp.com/."));
        }

        this.sendOpenA801OutfitsWindow(player);
    }

    /**
     * Handles when the player wants to add a promotion.
     * @param player The player's instance.
     * @param promotionItemId The promotion's item id
     * @param startDate The promotion's start date.
     * @param endDate The promotion's end date.
     * @param discount The promotion's discount.
     */
    public void handleAddPromotion(GamePlayer player, String promotionItemId, String startDate, String endDate, int discount) {
        long startTime, endTime, currentTime = Utils.getUnixTime();
        int itemId;
        try {
            startTime = Long.parseLong(startDate);
            endTime = Long.parseLong(endDate);
            itemId = Integer.parseInt(promotionItemId);
        } catch (NumberFormatException e) {
            player.broadcastPacket(new PacketSendServerMessage(true, "Invalid timestamp. Get one at https://www.unixtimestamp.com/."));
            this.sendOpenA801PromotionsWindow(player);
            return;
        }

        if (startTime <= 0 || endTime <= 0 || startTime >= endTime) {
            player.broadcastPacket(new PacketSendServerMessage(true, "Invalid promotion date."));
            this.sendOpenA801PromotionsWindow(player);
            return;
        }

        if (endTime <= currentTime) {
            player.broadcastPacket(new PacketSendServerMessage(true, "The promotion end date has already expired."));
            this.sendOpenA801PromotionsWindow(player);
            return;
        }

        if (discount < 1 || discount > 100) {
            player.broadcastPacket(new PacketSendServerMessage(true, "Discount must be between 1 and 100."));
            this.sendOpenA801PromotionsWindow(player);
            return;
        }

        if (this.server.getConfig().getGameConfig().getShopPromotions().stream().anyMatch(p -> p.getItemId() == itemId)) {
            player.broadcastPacket(new PacketSendServerMessage(true, "This item already has a promotion."));
            this.sendOpenA801PromotionsWindow(player);
            return;
        }

        this.server.getConfig().getGameConfig().getShopPromotions().add(new GameConfig.ShopPromotion(this.server.getNextDatabaseCounterId("lastPromotionId"), itemId, false, startTime, endTime, discount));
        this.sendOpenA801PromotionsWindow(player);
    }

    /**
     * Handles when the player want to remove an outfit.
     * @param player The player's instance.
     * @param outfitId The outfit's id.
     */
    public void handleRemoveOutfit(GamePlayer player, int outfitId) {
        this.server.getConfig().getGameConfig().getShopOutfits().removeIf(outfit -> outfit.getOutfitId() == outfitId);
        this.sendOpenA801OutfitsWindow(player);
    }

    /**
     * Handles when the player want to remove a promotion.
     * @param player The player's instance.
     * @param promotionId The promotion's id.
     */
    public void handleRemovePromotion(GamePlayer player, int promotionId) {
        this.server.getConfig().getGameConfig().getShopPromotions().removeIf(promotion -> promotion.getPromotionId() == promotionId);
        this.sendOpenA801PromotionsWindow(player);
    }

    /**
     * Handles when the player customizes a shop shaman item.
     * @param player The player's instance.
     * @param itemId The item's id.
     * @param colors The item's colors.
     */
    public void sendCustomizeShamanItem(GamePlayer player, int itemId, ArrayList<Integer> colors) {
        String data = player.getPlayerData().getShopShamanItems().get(itemId);
        if (data == null || !data.endsWith("_"))
            return;

        String[] newColors = new String[colors.size()];
        for (int i = 0; i < colors.size(); i++)
            newColors[i] = String.format("%06X", colors.get(i) & 0xFFFFFF);

        player.getPlayerData().getShopShamanItems().put(itemId, itemId + "_" + String.join("+", newColors));
        this.sendEquipShamanItem(player, itemId);
    }

    /**
     * Handles when the player equips a banner.
     * @param player The player's instance.
     * @param bannerId The banner's id.
     */
    public void sendEquipBanner(GamePlayer player, int bannerId) {
        if (!player.getPlayerData().getShopAdventureBanners().contains(bannerId))
            return;

        player.getPlayerData().currentAdventureBanner = bannerId;
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Equips a cloth.
     * @param player The player's instance.
     * @param clotheId The clothe id.
     */
    public void sendEquipClothe(GamePlayer player, int clotheId) {
        String pref = String.format("%02d/", clotheId);
        for (String clothe : player.getPlayerData().getShopClothes()) {
            if (clothe.startsWith(pref)) {
                player.getPlayerData().playerLook = clothe.substring(pref.length());
                break;
            }
        }

        this.sendOpenShopWindow(player, false);
        this.sendShopLookChange(player);
    }

    /**
     * Equips a shaman item.
     * @param player The player's instance.
     * @param itemId The shaman item id.
     */
    public void sendEquipShamanItem(GamePlayer player, int itemId) {
        if (!player.getPlayerData().getShopShamanItems().containsKey(itemId))
            return;

        String[] lookItems = player.getPlayerData().shamanLook.split(",");
        String custom = player.getPlayerData().getShopShamanItems().get(itemId);
        int category = this.getShamanLookPosition(itemId);
        String value = custom.isEmpty() ? String.valueOf(itemId) : custom;
        if (value.endsWith("_"))
            value = value.substring(0, value.length() - 1);

        if (lookItems[category].equals(value))
            lookItems[category] = "0";
        else
            lookItems[category] = value;

        player.getPlayerData().shamanLook = String.join(",", lookItems);
        this.sendOpenShopWindow(player, false);
        this.sendShopShamanLookChange(player);
    }

    /**
     * Initializes the shop at login.
     */
    public void sendLoginShopInitialization(GamePlayer player) {
        player.broadcastPacket(new PacketSendShopTimestamp(Utils.getUnixTime()));
        player.broadcastPacket(new PacketSendShopLoadItemSprites(this.server.getConfig().getGameConfig().getShopItemSprites()));
        player.broadcastPacket(new PacketSendShopLoadShamanSprites(this.server.getConfig().getGameConfig().getShopShamanSprites()));
        if (!player.getIsGuest()) {
            for (GameConfig.ShopPromotion promotion : this.server.getConfig().getGameConfig().getShopPromotions()) {
                if (promotion.getExpirationDate() > Utils.getUnixTime()) {
                    player.broadcastPacket(new PacketSendShopPromotion(promotion));
                }
            }
        }
    }

    /**
     * Handles when the player want to favorite a shop item.
     * @param player The player's instance.
     * @param itemId The shop item's id.
     * @param isFavorited Is the shop item favorited.
     */
    public void sendMarkFavoriteItem(GamePlayer player, int itemId, boolean isFavorited) {
        if (isFavorited)
            player.getPlayerData().getShopFavoriteItems().add(itemId);
        else
            player.getPlayerData().getShopFavoriteItems().remove(itemId);

        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player purchases a banner from the shop.
     * @param player The player's instance.
     * @param bannerId The banner's id.
     */
    public void sendPurchaseBanner(GamePlayer player, int bannerId) {
        if (player.getPlayerData().getShopAdventureBanners().contains(bannerId))
            return;

        ShopConfig.ShopBannerConfig config = this.server.getShopConfig().getShopBanners().get(bannerId);
        if (config == null)
            return;

        if (player.getPlayerData().cheeseCount < config.getCheesePrice())
            return;

        player.getPlayerData().cheeseCount -= config.getCheesePrice();
        player.getPlayerData().getShopAdventureBanners().add(bannerId);
        this.sendBuyResult(player, bannerId, 4);
        player.getParseDailyQuests().increaseMission(MissionType.BUY_SHOP_ITEM, 1);
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player purchases a shop clothe.
     * @param player The player's instance.
     * @param clotheId The clothe's id.
     * @param usingStrawberries Is using strawberries to purchase.
     */
    public void sendPurchaseClothe(GamePlayer player, int clotheId, boolean usingStrawberries) {
        int cheeseAmount;
        int strawberriesAmount;
        switch (clotheId) {
            case 0 -> {
                cheeseAmount = 40;
                strawberriesAmount = 5;
            }
            case 1 -> {
                cheeseAmount = 1000;
                strawberriesAmount = 50;
            }
            case 2 -> {
                cheeseAmount = 2000;
                strawberriesAmount = 100;
            }
            default -> {
                cheeseAmount = 4000;
                strawberriesAmount = 100;
            }
        }

        if (usingStrawberries) {
            if (player.getPlayerData().shopStrawberries < strawberriesAmount)
                return;

            player.getPlayerData().shopStrawberries -= strawberriesAmount;
        } else {
            if (player.getPlayerData().shopCheese < cheeseAmount)
                return;

            player.getPlayerData().shopCheese -= cheeseAmount;
        }

        player.getPlayerData().getShopClothes().add(String.format("%02d/1;0,0,0,0,0,0,0,0,0,0,0,0/%06X/%06X", clotheId, player.getPlayerData().mouseColor & 0xFFFFFF, player.getPlayerData().shamanColor & 0xFFFFFF));
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player purchases a emoji from the shop.
     * @param player The player's instance.
     * @param emojiId The emoji's id.
     * @param usingStrawberries is using strawberries to purchase.
     */
    public void sendPurchaseEmoji(GamePlayer player, int emojiId, boolean usingStrawberries) {
        if (player.getPlayerData().getShopEmojis().contains(emojiId))
            return;

        ShopConfig.ShopEmojiConfig config = this.server.getShopConfig().getShopEmoji().get(emojiId);
        if (config == null)
            return;

        int price = usingStrawberries ? config.getStrawberryPrice() : config.getCheesePrice();
        if (usingStrawberries) {
            if (player.getPlayerData().shopStrawberries < price)
                return;

            player.getPlayerData().shopStrawberries -= price;
        } else {
            if (player.getPlayerData().cheeseCount < price)
                return;

            player.getPlayerData().cheeseCount -= price;
        }

        player.getPlayerData().getShopEmojis().add(emojiId);
        this.sendBuyResult(player, emojiId, 3);
        player.getParseDailyQuests().increaseMission(MissionType.BUY_SHOP_ITEM, 1);
        this.sendOpenShopWindow(player, false);
        player.broadcastPacket(new PacketSendPlayerAvailableEmoji(player.getPlayerData().getShopEmojis()));
    }

    /**
     * Handles when the player purchases a shop shaman item.
     * @param player The player's instance.
     * @param itemId The shop item's id.
     * @param usingStrawberries Is using strawberries to purchase.
     */
    public void sendPurchaseShamanItem(GamePlayer player, int itemId, boolean usingStrawberries) {
        if (player.getPlayerData().getShopShamanItems().containsKey(itemId))
            return;

        ShopConfig.ShopShamanItemConfig config = this.server.getShopConfig().getShopShamanItems().get(itemId);
        if (config == null)
            return;

        int price = usingStrawberries ? config.getStrawberryPrice() : config.getCheesePrice();
        if (usingStrawberries) {
            if (player.getPlayerData().shopStrawberries < price)
                return;

            player.getPlayerData().shopStrawberries -= price;
        } else {
            if (player.getPlayerData().shopCheese < price)
                return;

            player.getPlayerData().shopCheese -= price;
        }

        player.getPlayerData().getShopShamanItems().put(itemId, "");
        this.sendBuyResult(player, itemId, 4);
        player.getParseDailyQuests().increaseMission(MissionType.BUY_SHOP_ITEM, 1);
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player purchases a customization for a shop shaman item.
     * @param player The player's instance.
     * @param itemId The shop shaman item's id.
     * @param usingStrawberries Is using strawberries to purchase.
     */
    public void sendPurchaseShamanItemCustom(GamePlayer player, int itemId, boolean usingStrawberries) {
        if (!player.getPlayerData().getShopShamanItems().containsKey(itemId))
            return;

        if (usingStrawberries) {
            if (player.getPlayerData().shopStrawberries < 150)
                return;

            player.getPlayerData().shopStrawberries -= 150;
        } else {
            if (player.getPlayerData().shopCheese < 4000)
                return;

            player.getPlayerData().shopCheese -= 4000;
        }

        player.getPlayerData().getShopShamanItems().put(itemId, itemId + "_");
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player want to open the promotions manager.
     * @param player The player's instance.
     */
    public void sendOpenA801PromotionsWindow(GamePlayer player) {
        player.broadcastPacket(new PacketSendStaffOpenPromotionsWindow(this.server.getConfig().getGameConfig().getShopPromotions(), player.hasStaffPermission(StaffRoleType.ADMIN, false)));
    }

    /**
     * Handles when the player want to open the outfits manager.
     * @param player The player's instance.
     */
    public void sendOpenA801OutfitsWindow(GamePlayer player) {
        player.broadcastPacket(new PacketSendStaffOpenOutfitsWindow(this.server.getConfig().getGameConfig().getShopOutfits(), player.hasStaffPermission(StaffRoleType.ADMIN, false)));
    }

    /**
     * Saves a cloth.
     * @param clotheId The clothe id.
     */
    public void sendSaveClothe(GamePlayer player, int clotheId) {
        List<String> shopClothes = player.getPlayerData().getShopClothes();
        String pref = String.format("%02d/", clotheId);
        for (int i = 0; i < shopClothes.size(); i++) {
            String clothe = shopClothes.get(i);
            if (clothe.startsWith(pref)) {
                String[] parts = clothe.split("/");
                parts[1] = player.getPlayerData().playerLook;
                parts[2] = String.format("%06X", player.getPlayerData().mouseColor & 0xFFFFFF);
                parts[3] = String.format("%06X", player.getPlayerData().shamanColor & 0xFFFFFF);
                shopClothes.set(i, String.join("/", parts));
                break;
            }
        }

        this.sendOpenShopWindow(player, false);
    }

    /**
     * Sends the shop currency (Cheeses & Strawberries/Fraises).
     * @param player The player's instance.
     */
    public void sendShopCurrency(GamePlayer player) {
        player.broadcastPacket(new PacketSendShopCurrency(player.getPlayerData().shopCheese, player.getPlayerData().shopStrawberries));
    }

    /**
     * Gets the shaman look's position.
     * @param itemId The shaman item's id.
     * @return The shaman look's position.
     */
    private int getShamanLookPosition(int itemId) {
        return switch (itemId / 100) {
            case 1 -> 0; // small box (100-199)
            case 2 -> 1; // large box (200-299)
            case 3 -> 2; // short plank (300-399)
            case 4 -> 3; // long plank (400-499)
            case 17 -> 4; // cannonball (1700-1799)
            case 28 -> 5; // balloon (2800-2899)
            case 10 -> 6; // anvil (1000-1099)
            case 6 -> 7; // ball (600-699)
            case 7 -> 8; // trampoline (700-799)
            default -> -1;
        };
    }

    /**
     * Sends the updated shaman look.
     */
    private void sendShopShamanLookChange(GamePlayer player) {
        player.broadcastPacket(new PacketSendShamanLookChange(player.getPlayerData().shamanLook));
    }





    public void sendEquipItem(GamePlayer player, int i) {
        ///  TODO: IMPLEMENT
    }

    public void sendCustomizeItem(GamePlayer player, int shopItemId, ArrayList<Integer> colors) {
        ///  TODO: IMPLEMENT
    }


    public void sendPurchaseItem(GamePlayer player, int i, boolean b, short i1) {
        ///  TODO: IMPLEMENT
    }

    public void sendPurchaseItemCustom(GamePlayer player, int i, boolean b) {
        ///  TODO: IMPLEMENT
    }












    private void sendBuyResult(GamePlayer player, int bannerId, int i) {
        ///  TODO: IMPLEMENT
    }

    public void sendOpenDressingWindow(GamePlayer player) {
        ///  TODO: IMPLEMENT
    }

    public void handleGiftResult(GamePlayer player, int i, boolean b, String s, boolean b1) {
        ///  TODO: IMPLEMENT
    }

    public void handleGift(GamePlayer player, String s, boolean b, int i, String s1) {
        ///  TODO: IMPLEMENT
    }

    public void sendOpenShopWindow(GamePlayer player, boolean isReloading) {
        ///  TODO: IMPLEMENT
    }

    public void sendVisualizeShopOutfit(GamePlayer player, short i) {
        ///  TODO: IMPLEMENT
    }

    private void sendShopLookChange(GamePlayer player) {
        ///  TODO: IMPLEMENT
    }
}