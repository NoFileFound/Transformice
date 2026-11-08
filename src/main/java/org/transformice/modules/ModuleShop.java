package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.config.shop.ShopConfig;
import org.transformice.config.tfmconfig.GameConfig;
import org.transformice.data.objects.MissionType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.database.data.ShopgiftEntry;
import org.transformice.modules.impl.ModuleServer;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.PacketSendServerMessage;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;
import org.transformice.packets.send.player.PacketSendOpenShopInterface;
import org.transformice.packets.send.player.PacketSendPlayerRaiseItem;
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
     * Converts a shop item's category and id into the single numeric id the client uses
     * (in purchases, owned-item lists, promotions and outfit previews):
     * ids under 100 are category*100 + id; ids of 100 and up are (category+1)*10000 + id,
     * which also covers furs (22_184 -> 230184).
     * @param category The item category.
     * @param itemId The item id inside that category.
     * @return The client's numeric id.
     */
    public static int getClientShopInfo(int category, int itemId) {
        return itemId >= 100 ? (category + 1) * 10000 + itemId : category * 100 + itemId;
    }

    /**
     * Gets the item category and item id from full item.
     * @param fullItemId The full item.
     * @return An array containing the category at index 0 and item id at index 1.
     */
    public int[] getServerShopInfo(int fullItemId) {
        // Pelages (category 22) with 3-digit IDs use the special "230" + id format.
        if (fullItemId >= 230000 && fullItemId < 231000)
            return new int[] {22, fullItemId - 230000};

        if (fullItemId > 9999) {
            // Client format: (category + 1) * 10000 + id.
            int itemCat = fullItemId / 10000 - 1;
            int itemId = fullItemId % 10000;
            if (this.server.getShopConfig().getShopItems().get(itemCat + "_" + itemId) == null) {
                int oldCat = (fullItemId - 10000) / 1000;
                int oldId = fullItemId % 1000;
                if (this.server.getShopConfig().getShopItems().get(itemCat + "_" + itemId) != null) {
                    return new int[] {oldCat, oldId};
                }
            }

            return new int[] {itemCat, itemId};
        }

        int itemCat = fullItemId / 100;
        int itemId = fullItemId > 999 ? fullItemId % 100 : fullItemId > 99 ? fullItemId % (100 * itemCat) : fullItemId;
        return new int[] {itemCat, itemId};
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
        try {
            startTime = Long.parseLong(startDate);
            endTime = Long.parseLong(endDate);
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

        if (this.server.getConfig().getGameConfig().getShopPromotions().stream().anyMatch(p -> p.getItemId().equals(promotionItemId))) {
            player.broadcastPacket(new PacketSendServerMessage(true, "This item already has a promotion."));
            this.sendOpenA801PromotionsWindow(player);
            return;
        }

        this.server.getConfig().getGameConfig().getShopPromotions().add(new GameConfig.ShopPromotion(this.server.getNextDatabaseCounterId("lastPromotionId"), promotionItemId, false, startTime, endTime, discount));
        this.sendOpenA801PromotionsWindow(player);
    }

    /**
     * Handles when the player sends a shop gift to another player.
     * @param player The player's instance (Sender).
     * @param playerName The player's name (Receiver).
     * @param isShamanItem Is the item regular or shaman.
     * @param itemId The item's id.
     * @param message The gift's message.
     */
    public void handleGift(GamePlayer player,String playerName, boolean isShamanItem, int itemId, String message) {
        PlayerEntity receiverAcc = this.server.getDatabase().loadPlayerByName(playerName);
        if (receiverAcc == null) {
            player.broadcastPacket(new PacketSendShopGiftResult(1, playerName));
            return;
        }

        playerName = receiverAcc.getUsername();
        if (isShamanItem ? receiverAcc.getShopShamanItems().containsKey(itemId) : receiverAcc.getShopItems().containsKey(itemId)) {
            player.broadcastPacket(new PacketSendShopGiftResult(2, playerName));
            return;
        }

        int price;
        if(isShamanItem) {
            var info = this.server.getShopConfig().getShopShamanItems().get(itemId);
            if (info == null)
                return;

            price = this.getPromotionPrice(itemId, false, info.getStrawberryPrice());
        } else {
            var shopInfo = this.getServerShopInfo(itemId);
            var info = this.server.getShopConfig().getShopItems().get(shopInfo[0] + "_" + shopInfo[1]);
            if (info == null)
                return;

            price = this.getPromotionPrice(itemId, true, info.getStrawberryPrice());
        }

        PlayerEntity sender = player.getPlayerData();
        if (price <= 0 || sender.shopStrawberries < price) {
            this.sendShopCurrency(player);
            return;
        }

        sender.shopStrawberries -= price;
        int giftId = this.server.getNextDatabaseCounterId("lastGiftId");
        this.server.getDatabase().saveShopGift(new ShopgiftEntry(giftId, receiverAcc.getUsername(), player.getPlayerName(), player.getPlayerData().playerLook, isShamanItem, itemId, message));
        GamePlayer receiver = this.server.getPlayers().get(playerName);
        if (receiver != null)
            receiver.broadcastPacket(new PacketSendShopGift(giftId, player.getPlayerName(), sender.playerLook, isShamanItem, itemId, message, false));

        player.broadcastPacket(new PacketSendShopGiftResult(0, playerName));
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player opens a shop gift.
     * @param player The player's instance.
     * @param giftId The gift's id.
     * @param isOpen Is the gift opened.
     * @param message The message.
     * @param isMessage Is sent a message.
     */
    public void handleGiftResult(GamePlayer player, int giftId, boolean isOpen, String message, boolean isMessage) {
        ShopgiftEntry gift = this.server.getDatabase().loadShopGiftById(giftId);
        if (gift == null || (isOpen && gift.hasOpened && !gift.getReceiver().equals(player.getPlayerName())))
            return;

        if (isOpen) {
            GamePlayer sender = this.server.getPlayers().get(gift.getSender());
            if (sender != null) {
                sender.broadcastPacket(new PacketSendTranslatedMessage("", "$DonItemRecu", new String[]{player.getPlayerName()}));
            }

            if (gift.getIsShamanItem()) {
                boolean isCustomizationEnabled = gift.getItemId() == 701;
                if (player.getPlayerData().getShopShamanItems().putIfAbsent(gift.getItemId(), (isCustomizationEnabled ? "_" : "")) == null)
                    this.sendBuyResult(player, BuyResult.SHAMAN_ITEM, gift.getItemId());
            } else if(!player.getPlayerData().getShopItems().containsKey(gift.getItemId())) {
                player.getPlayerData().getShopItems().put(gift.getItemId(), "");
                this.sendBuyResult(player, BuyResult.REGULAR_ITEM, gift.getItemId());
            }

            gift.hasOpened = true;
            this.sendOpenShopWindow(player, false);
        } else {
            gift.senderMessage = message;
            gift.hasRespond = true;
            GamePlayer sender = this.server.getPlayers().get(gift.getSender());
            if (sender != null) {
                gift.hasRespond = false;
                sender.broadcastPacket(new PacketSendShopGift(gift.getId(), gift.getReceiver(), gift.getSenderPlayerLook(), gift.getIsShamanItem(), gift.getItemId(), gift.senderMessage, true));
            }
        }
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
        GameConfig.ShopPromotion promotion = this.server.getConfig().getGameConfig().getShopPromotions().stream().filter(p -> p.getPromotionId() == promotionId).findFirst().orElse(null);
        if (promotion == null)
            return;

        for (GamePlayer client : this.server.getPlayers().values())
            client.broadcastPacket(new PacketSendShopPromotion(promotion, false));

        this.sendOpenA801PromotionsWindow(player);
    }

    /**
     * Handles when the player customizes a regular shop item.
     * @param player The player's instance.
     * @param itemId The item's id.
     * @param colors The item's colors.
     */
    public void sendCustomizeItem(GamePlayer player, int itemId, ArrayList<Integer> colors) {
        String data = player.getPlayerData().getShopItems().get(itemId);
        if (data == null)
            return;

        if (!data.contains("_")) {
            this.sendOpenShopWindow(player, false);
            return;
        }

        String[] newColors = new String[colors.size()];
        for (int i = 0; i < colors.size(); i++)
            newColors[i] = String.format("%06X", colors.get(i) & 0xFFFFFF);

        int[] info = this.getServerShopInfo(itemId);
        player.getPlayerData().getShopItems().put(itemId, info[1] + "_" + String.join("+", newColors));
        int category = this.getRegularLookPosition(itemId);
        String[] look = player.getPlayerData().playerLook.split(";", 2);
        if (category >= 0 && category != 23 && look.length == 2) {
            String[] lookItems = look[1].split(",");
            String itemNumber = String.valueOf(info[1]);
            if (category < lookItems.length && (lookItems[category].equals(itemNumber) || lookItems[category].startsWith(itemNumber + "_"))) {
                lookItems[category] = info[1] + "_" + String.join("+", newColors);
                player.getPlayerData().playerLook = look[0] + ';' + String.join(",", lookItems);
                this.sendShopLookChange(player);
            }
        }
    }

    /**
     * Handles when the player customizes a shop shaman item.
     * @param player The player's instance.
     * @param itemId The item's id.
     * @param colors The item's colors.
     */
    public void sendCustomizeShamanItem(GamePlayer player, int itemId, ArrayList<Integer> colors) {
        String data = player.getPlayerData().getShopShamanItems().get(itemId);
        if (data == null)
            return;

        if (!data.contains("_")) {
            this.sendOpenShopWindow(player, false);
            return;
        }

        String[] newColors = new String[colors.size()];
        for (int i = 0; i < colors.size(); i++)
            newColors[i] = String.format("%06X", colors.get(i) & 0xFFFFFF);

        player.getPlayerData().getShopShamanItems().put(itemId, itemId + "_" + String.join("+", newColors));
        this.sendEquipShamanItem(player, itemId);
    }

    /**
     * Handles when the player opens the dressing window.
     * @param player The player's instance.
     */
    public void sendOpenDressingWindow(GamePlayer player) {
        player.broadcastPacket(new PacketSendOpenDressingInterface(this.server.getShopConfig().getShopItems(), this.server.getConfig().getGameConfig().getShopPromotions()));
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
        if (clotheId < 0 || clotheId >= player.getPlayerData().getShopClothes().size())
            return;

        String[] parts = player.getPlayerData().getShopClothes().get(clotheId).split("/");
        player.getPlayerData().playerLook = parts[1];
        player.getPlayerData().mouseColor = Integer.parseInt(parts[2], 16);
        player.getPlayerData().shamanColor = Integer.parseInt(parts[3], 16);
        this.sendOpenShopWindow(player, false);
        this.sendShopLookChange(player);
    }

    /**
     * Handles when the player equips a regular item.
     * @param player The player's instance.
     * @param itemId The item's id.
     */
    public void sendEquipItem(GamePlayer player, int itemId) {
        int id = this.getRegularLookPosition(itemId);
        if (id == -1)
            return;

        if (id == 23) {
            int furId = this.getServerShopInfo(itemId)[1];
            if(itemId >= 2100 && itemId <= 2106) {
                int item_id2 = (player.getPlayerData().mouseColor == 12423271 ? 2100 : player.getPlayerData().mouseColor == 5846552 ? 2101 : player.getPlayerData().mouseColor == 9209983 ? 2102 : player.getPlayerData().mouseColor == 14670030 ? 2103 : player.getPlayerData().mouseColor == 5129274 ? 2104 : player.getPlayerData().mouseColor == 14925950 ? 2105 : player.getPlayerData().mouseColor == 2564640 ? 2106 : -1);
                if(itemId == item_id2) {
                    player.getPlayerData().mouseColor = 7886906;
                } else {
                    switch (itemId) {
                        case 2100:
                            player.getPlayerData().mouseColor = 12423271;
                            break;
                        case 2101:
                            player.getPlayerData().mouseColor = 5846552;
                            break;
                        case 2102:
                            player.getPlayerData().mouseColor = 9209983;
                            break;
                        case 2103:
                            player.getPlayerData().mouseColor = 14670030;
                            break;
                        case 2104:
                            player.getPlayerData().mouseColor = 5129274;
                            break;
                        case 2105:
                            player.getPlayerData().mouseColor = 14925950;
                            break;
                        case 2106:
                            player.getPlayerData().mouseColor = 2564640;
                            break;
                    }
                }

                player.getPlayerData().playerLook = player.getPlayerData().playerLook.replaceFirst("^\\d+;", "1;");
                this.sendOpenShopWindow(player, false);
                this.sendShopLookChange(player);
                return;
            }

            player.getPlayerData().mouseColor = 7886906;
            if (player.getPlayerData().playerLook.startsWith(furId + ";")) {
                player.getPlayerData().playerLook = player.getPlayerData().playerLook.replaceFirst(String.valueOf(furId), "1");
            } else {
                player.getPlayerData().playerLook = player.getPlayerData().playerLook.replaceFirst("^\\d+", String.valueOf(furId));
            }

            this.sendOpenShopWindow(player, false);
            this.sendShopLookChange(player);
            return;
        }

        String[] lookItems = player.getPlayerData().playerLook.split(";", 2)[1].split(",");
        String custom = player.getPlayerData().getShopItems().get(itemId);
        String value = (custom.isEmpty()) ? String.valueOf(this.getServerShopInfo(itemId)[1]) : custom;
        if (value.endsWith("_"))
            value = value.substring(0, value.length() - 1);

        String itemNumber = String.valueOf(this.getServerShopInfo(itemId)[1]);
        String worn = lookItems[id];
        if (worn.equals(itemNumber) || worn.startsWith(itemNumber + "_")) {
            if (worn.startsWith(itemNumber + "_") && worn.length() > itemNumber.length() + 1) {
                player.getPlayerData().getShopItems().put(itemId, worn);
            }
            lookItems[id] = "0";
        } else {
            lookItems[id] = value;
        }

        player.getPlayerData().playerLook = player.getPlayerData().playerLook.split(";", 2)[0] + ';' + String.join(",", lookItems);
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

        String custom = player.getPlayerData().getShopShamanItems().get(itemId);
        int category = this.getShamanLookPosition(itemId);
        if (category == -1)
            return;

        String value = custom.isEmpty() ? String.valueOf(itemId) : custom;
        if (value.endsWith("_"))
            value = value.substring(0, value.length() - 1);

        List<String> lookItems = new ArrayList<>(Arrays.asList(player.getPlayerData().shamanLook.split(",")));

        int pos = -1;
        for (int i = 0; i < lookItems.size(); i++) {
            String item = lookItems.get(i).trim();
            int colors = item.indexOf('_');
            if ((colors > 0 ? item.substring(0, colors) : item).equals(String.valueOf(itemId))) {
                pos = i;
                break;
            }
        }

        if (pos >= 0) {
            lookItems.remove(pos);
        } else {
            lookItems.add(value);
        }

        player.getPlayerData().shamanLook = lookItems.isEmpty() ? "0" : String.join(",", lookItems);
        this.sendOpenShopWindow(player, false);
        this.sendShopShamanLookChange(player);
    }

    /**
     * Initializes the shop at login.
     * @param player The player's instance.
     */
    public void sendLoginShopInitialization(GamePlayer player) {
        player.broadcastPacket(new PacketSendShopTimestamp(Utils.getUnixTime()));
        player.broadcastPacket(new PacketSendShopLoadItemSprites(this.server.getConfig().getGameConfig().getShopItemSprites()));
        player.broadcastPacket(new PacketSendShopLoadShamanSprites(this.server.getConfig().getGameConfig().getShopShamanSprites()));
        if (!player.getIsGuest()) {
            for (GameConfig.ShopPromotion promotion : this.server.getConfig().getGameConfig().getShopPromotions()) {
                if (promotion.getExpirationDate() > Utils.getUnixTime()) {
                    player.broadcastPacket(new PacketSendShopPromotion(promotion, true));
                }
            }

            List<ShopgiftEntry> playerGifts = this.server.getDatabase().loadShopGiftsByReceiver(player.getPlayerName(), false);
            for (ShopgiftEntry entry : playerGifts) {
                player.broadcastPacket(new PacketSendShopGift(entry.getId(), entry.getReceiver(), entry.getSenderPlayerLook(), entry.getIsShamanItem(), entry.getItemId(), entry.getMessage(), false));
            }

            List<ShopgiftEntry> playerMessages = this.server.getDatabase().loadShopGiftsByReceiver(player.getPlayerName(), true);
            for (ShopgiftEntry entry : playerMessages) {
                player.broadcastPacket(new PacketSendShopGift(entry.getId(), entry.getReceiver(), entry.getSenderPlayerLook(), entry.getIsShamanItem(), entry.getItemId(), entry.senderMessage, true));
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

        if (player.getPlayerData().shopCheese < config.getCheesePrice())
            return;

        player.getPlayerData().shopCheese -= config.getCheesePrice();
        player.getPlayerData().getShopAdventureBanners().add(bannerId);
        this.sendBuyResult(player, BuyResult.BANNER, bannerId);
        System.out.println("OK");
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player purchases a shop clothe.
     * @param player The player's instance.
     * @param clotheId The clothe's id.
     * @param usingStrawberries Is using strawberries to purchase.
     */
    public void sendPurchaseClothe(GamePlayer player, int clotheId, boolean usingStrawberries) {
        if (clotheId < player.getPlayerData().getShopClothes().size())
            return;

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
            if (player.getPlayerData().shopCheese < price)
                return;

            player.getPlayerData().shopCheese -= price;
        }

        player.getPlayerData().getShopEmojis().add(emojiId);
        player.broadcastPacket(new PacketSendPlayerAvailableEmoji(player.getPlayerData().getShopEmojis()));
        this.sendBuyResult(player, BuyResult.EMOJI, emojiId);
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player purchases a regular shop item.
     * @param player The player's instance.
     * @param itemId The item's id.
     * @param usingStrawberries Is using strawberries to purchase.
     */
    public void sendPurchaseItem(GamePlayer player, int itemId, boolean usingStrawberries) {
        if (player.getPlayerData().getShopItems().containsKey(itemId))
            return;

        int[] shopInfo = this.getServerShopInfo(itemId);
        ShopConfig.ShopItemConfig config = this.server.getShopConfig().getShopItems().get(shopInfo[0] + "_" + shopInfo[1]);
        if (config == null)
            return;

        int price = usingStrawberries ? this.getPromotionPrice(itemId, true, config.getStrawberryPrice()) : config.getCheesePrice();
        if (usingStrawberries) {
            if (player.getPlayerData().shopStrawberries < price)
                return;

            player.getPlayerData().shopStrawberries -= price;
        } else {
            if (player.getPlayerData().shopCheese < price)
                return;

            player.getPlayerData().shopCheese -= price;
        }

        if (shopInfo[0] == 0 && shopInfo[1] == 3) {
            player.getPlayerData().getShopItems().put(itemId, "_");
        } else {
            player.getPlayerData().getShopItems().put(itemId, "");
        }

        this.sendBuyResult(player, BuyResult.REGULAR_ITEM, itemId);
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Handles when the player purchases a customization for a regular shop item.
     * @param player The player's instance.
     * @param itemId The shop shaman item's id.
     * @param usingStrawberries Is using strawberries to purchase.
     */
    public void sendPurchaseItemCustom(GamePlayer player, int itemId, boolean usingStrawberries) {
        if (!player.getPlayerData().getShopItems().containsKey(itemId))
            return;

        int[] shopInfo = this.getServerShopInfo(itemId);
        if (!(player.getPlayerData().getShopItems().get(itemId).contains("_") || (shopInfo[0] == 0 && shopInfo[1] == 3))) {
            if (usingStrawberries) {
                if (player.getPlayerData().shopStrawberries < 20)
                    return;

                player.getPlayerData().shopStrawberries -= 20;
            } else {
                if (player.getPlayerData().shopCheese < 2000)
                    return;

                player.getPlayerData().shopCheese -= 2000;
            }
        }

        player.getPlayerData().getShopItems().put(itemId, shopInfo[1] + "_");
        this.sendOpenShopWindow(player, false);
    }

    /**
     * Buys every piece of the given outfit at once (the fur and every non-owned
     * clothing piece), charging the sum in strawberries, then wears the resulting look.
     * @param outfitId The outfit id.
     */
    public void sendPurchaseOutfit(GamePlayer player, int outfitId) {
        var outfitInfo = this.server.getConfig().getGameConfig().getShopOutfits().stream().filter(outfit -> outfit.getOutfitId() == outfitId).findFirst().orElse(null);
        if (outfitInfo == null)
            return;

        String[] outfitLook = outfitInfo.getOutfitLook().split(";");
        int furId = Integer.parseInt(outfitLook[0]);
        int furFullId = getClientShopInfo(22, furId);
        var furInfo = this.server.getShopConfig().getShopItems().get("22_" + furId);
        boolean ownsFur = player.getPlayerData().getShopItems().containsKey(furFullId);
        int totalPrice = 0;
        if (!ownsFur && furInfo != null)
            totalPrice += this.getOutfitPiecePrice(furFullId, furInfo.getStrawberryPrice());

        List<Integer> piecesToBuy = new ArrayList<>();
        Map<Integer, String> pieceCustoms = new HashMap<>();
        int i = 0;
        for (String visualItem : outfitLook[1].split(",")) {
            if (!visualItem.equals("0")) {
                boolean hasCustom = visualItem.contains("_");
                int itemId = hasCustom ? Integer.parseInt(visualItem.substring(0, visualItem.indexOf("_"))) : Integer.parseInt(visualItem);
                int fullItemId = getClientShopInfo(i, itemId);
                var pieceInfo = this.server.getShopConfig().getShopItems().get(i + "_" + itemId);
                if (pieceInfo != null) {
                    String owned = player.getPlayerData().getShopItems().get(fullItemId);
                    if (owned == null) {
                        totalPrice += this.getOutfitPiecePrice(fullItemId, pieceInfo.getStrawberryPrice());
                        piecesToBuy.add(fullItemId);
                    }

                    if ((owned == null || !owned.contains("_")) && (hasCustom || pieceInfo.getColorNum() > 0))
                        pieceCustoms.put(fullItemId, hasCustom ? visualItem : itemId + "_");
                }
            }

            i++;
        }

        if (player.getPlayerData().shopStrawberries < totalPrice)
            return;

        player.getPlayerData().shopStrawberries -= totalPrice;
        if (!ownsFur && furInfo != null)
            player.getPlayerData().getShopItems().put(furFullId, "");

        for(int fullItemId : piecesToBuy)
            player.getPlayerData().getShopItems().put(fullItemId, "");

        player.getPlayerData().getShopItems().putAll(pieceCustoms);
        if (!ownsFur && furInfo != null)
            this.sendBuyResult(player, BuyResult.REGULAR_ITEM, getClientShopInfo(22, furId));

        for (int fullItemId : piecesToBuy)
            this.sendBuyResult(player, BuyResult.REGULAR_ITEM, fullItemId);

        player.getPlayerData().playerLook = outfitInfo.getOutfitLook();
        List<String> clothes = player.getPlayerData().getShopClothes();
        boolean saved = clothes.stream().anyMatch(clothe -> clothe.split("/")[1].equals(outfitInfo.getOutfitLook()));
        if (!saved)
            clothes.add(String.format("%02d/%s/%06X/%06X", clothes.size(), outfitInfo.getOutfitLook(), player.getPlayerData().mouseColor & 0xFFFFFF, player.getPlayerData().mouseColor & 0xFFFFFF));

        this.sendOpenShopWindow(player, false);
        this.sendShopLookChange(player);
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

        int price = usingStrawberries ? this.getPromotionPrice(itemId, false, config.getStrawberryPrice()) : config.getCheesePrice();
        if (usingStrawberries) {
            if (player.getPlayerData().shopStrawberries < price)
                return;

            player.getPlayerData().shopStrawberries -= price;
        } else {
            if (player.getPlayerData().shopCheese < price)
                return;

            player.getPlayerData().shopCheese -= price;
        }

        player.getPlayerData().getShopShamanItems().put(itemId, (itemId == 701 ? "_" : ""));
        this.sendBuyResult(player, BuyResult.SHAMAN_ITEM, itemId);
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

        if (!(player.getPlayerData().getShopShamanItems().get(itemId).contains("_") || itemId == 701)) {
            if (usingStrawberries) {
                if (player.getPlayerData().shopStrawberries < 150)
                    return;

                player.getPlayerData().shopStrawberries -= 150;
            } else {
                if (player.getPlayerData().shopCheese < 4000)
                    return;

                player.getPlayerData().shopCheese -= 4000;
            }
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
     * Handles when the player opens the shop.
     * @param player The player's instance.
     * @param isReloading Should send the shop config or not.
     */
    public void sendOpenShopWindow(GamePlayer player, boolean isReloading) {
        player.broadcastPacket(new PacketSendOpenShopInterface(this.server.getShopConfig(), isReloading, player.getPlayerData().getShopAdventureBanners(), player.getPlayerData().getShopClothes(), player.getPlayerData().getShopEmojis(), player.getPlayerData().getShopFavoriteItems(), player.getPlayerData().getShopItems(), player.getPlayerData().getShopShamanItems(), this.server.getConfig().getGameConfig().getShopOutfits(), player.getPlayerData().shopCheese, player.getPlayerData().shopStrawberries, player.getPlayerData().playerLook, player.getPlayerData().shamanLook, player.getPlayerData().currentAdventureBanner));
    }

    /**
     * Saves a cloth.
     * @param player The player's instance.
     * @param clotheId The clothe id.
     */
    public void sendSaveClothe(GamePlayer player, int clotheId) {
        List<String> shopClothes = player.getPlayerData().getShopClothes();
        if (clotheId < 0 || clotheId >= shopClothes.size())
            return;

        String[] parts = shopClothes.get(clotheId).split("/");
        parts[1] = player.getPlayerData().playerLook;
        parts[2] = String.format("%06X", player.getPlayerData().mouseColor & 0xFFFFFF);
        parts[3] = String.format("%06X", player.getPlayerData().shamanColor & 0xFFFFFF);
        shopClothes.set(clotheId, String.join("/", parts));
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
     * Sends the visual shop outfit.
     * @param player The player's instance.
     * @param outfitId The outfit's id.
     */
    public void sendVisualizeShopOutfit(GamePlayer player, int outfitId) {
        var outfitInfo = this.server.getConfig().getGameConfig().getShopOutfits().stream().filter(outfit -> outfit.getOutfitId() == outfitId).findFirst().orElse(null);
        if (outfitInfo == null)
            return;

        String[] outfitLook = outfitInfo.getOutfitLook().split(";");
        int i = 0;
        List<Object[]> infos = new ArrayList<>();
        int furId = Integer.parseInt(outfitLook[0]);
        if (furId != 1) {
            int furFullId = getClientShopInfo(22, furId);
            var furInfo = this.server.getShopConfig().getShopItems().get("22_" + furId);
            int furStrawberryPrice = furInfo == null ? 0 : furInfo.getStrawberryPrice();
            infos.add(new Object[]{furId, "", furFullId, player.getPlayerData().getShopItems().containsKey(furFullId), false, furStrawberryPrice, this.getOutfitPiecePrice(furFullId, furStrawberryPrice)});
        }

        for (String visualItem : outfitLook[1].split(",")) {
            if (!visualItem.equals("0")) {
                int itemId;
                String itemCustom;
                boolean hasCustom = visualItem.contains("_");
                if(hasCustom) {
                    itemId = Integer.parseInt(visualItem.substring(0, visualItem.indexOf("_")));
                    itemCustom = visualItem.substring(visualItem.indexOf("_") + 1);
                } else {
                    itemId = Integer.parseInt(visualItem);
                    itemCustom = "";
                }

                int fullItemId = getClientShopInfo(i, itemId);
                var pieceInfo = this.server.getShopConfig().getShopItems().get(i + "_" + itemId);
                int pieceStrawberryPrice = pieceInfo == null ? 0 : pieceInfo.getStrawberryPrice();
                infos.add(new Object[]{itemId, itemCustom, fullItemId, player.getPlayerData().getShopItems().containsKey(fullItemId), false, pieceStrawberryPrice, this.getOutfitPiecePrice(fullItemId, pieceStrawberryPrice)});
            }

            i++;
        }

        player.broadcastPacket(new PacketSendVisualizeShopOutfit(outfitId, outfitInfo.getOutfitLook(), infos));
    }

    /**
     * Gets the promotional price of an item if it has it.
     * @param itemId The item's id.
     * @param isRegularItem Is the item regular or shaman one.
     * @param initialStrawberryPrice The item's initial strawberry price.
     * @return The promotional price.
     */
    private int getPromotionPrice(int itemId, boolean isRegularItem, int initialStrawberryPrice) {
        String promotionItemId;
        if (isRegularItem) {
            int[] shopInfo = this.getServerShopInfo(itemId);
            promotionItemId = shopInfo[0] + "_" + shopInfo[1];
        } else {
            promotionItemId = String.valueOf(itemId);
        }

        long currentTime = System.currentTimeMillis();
        for (GameConfig.ShopPromotion promotion : this.server.getConfig().getGameConfig().getShopPromotions()) {
            if (!promotion.getItemId().equals(promotionItemId))
                continue;

            if (promotion.getIsShamanItem() == isRegularItem)
                continue;

            if (currentTime < promotion.getStartDate() || currentTime > promotion.getExpirationDate())
                continue;

            int discount = Math.max(0, Math.min(100, promotion.getDiscount()));
            return (int) Math.round(initialStrawberryPrice * (100 - discount) / 100.0);
        }

        return initialStrawberryPrice;
    }

    /**
     * Gets the regular look's position.
     * @param itemId The regular item's id.
     * @return The regular look's position.
     */
    private int getRegularLookPosition(int itemId) {
        if ((itemId > 0 && itemId <= 99) || (itemId >= 10000 && itemId <= 19999))
            return 0; // head

        if (itemId >= 100 && itemId <= 199 || (itemId >= 20000 && itemId <= 29999))
            return 1; // eyes

        if (itemId >= 200 && itemId <= 299 || (itemId >= 30000 && itemId <= 39999))
            return 2; // ears

        if (itemId >= 300 && itemId <= 399 || (itemId >= 40000 && itemId <= 49999))
            return 3; // mouth

        if (itemId >= 400 && itemId <= 499 || (itemId >= 50000 && itemId <= 59999))
            return 4; // neck

        if (itemId >= 500 && itemId <= 599 || (itemId >= 60000 && itemId <= 69999))
            return 5; // hairstyle

        if (itemId >= 600 && itemId <= 699 || (itemId >= 70000 && itemId <= 79999))
            return 6; // tail

        if (itemId >= 700 && itemId <= 799 || (itemId >= 80000 && itemId <= 89999))
            return 7; // contact leans

        if (itemId >= 800 && itemId <= 899 || (itemId >= 90000 && itemId <= 99999))
            return 8; // hands

        if (itemId >= 1100 && itemId <= 1199)
            return 11; // tattoos

        return 23; // furs.
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
     * The price of one piece when it is bought with its whole outfit: its price (with its promotion, if it has one)
     * minus the outfit discount.
     */
    private int getOutfitPiecePrice(int fullItemId, int price) {
        int promoted = this.getPromotionPrice(fullItemId, true, price);
        return (int) Math.round(promoted * 80 / 100.0);
    }

    /**
     * Sends the player's purchase.
     * @param player The player's instance.
     * @param itemType The item's type.
     * @param itemId The item's id.
     */
    private void sendBuyResult(GamePlayer player, BuyResult itemType, int itemId) {
        player.getParseDailyQuests().increaseMission(MissionType.BUY_SHOP_ITEM, 1);
        if (itemType == BuyResult.REGULAR_ITEM) {
            player.broadcastPacket(new PacketSendPurchaseRegularItem(itemId));
            player.getPlayerRoom().broadcastPacket(new PacketSendPlayerRaiseItem(player.getSessionId(), 0, itemId));
            int[] shopInfo = this.getServerShopInfo(itemId);
            if (this.server.getShopConfig().getShopItemsBadges().containsKey(shopInfo[0] + "_" + shopInfo[1]))
                player.sendUnlockBadge(this.server.getShopConfig().getShopItemsBadges().get(shopInfo[0] + "_" + shopInfo[1]));

            player.checkTitleProgress(player.getPlayerData().getShopItems().size(), "shop");
        }
    }

    /**
     * Sends the updated player look.
     * @param player The player's instance.
     */
    private void sendShopLookChange(GamePlayer player) {
        player.broadcastPacket(new PacketSendShopRegularLookChange(player.getPlayerData().playerLook, player.getPlayerData().mouseColor));
    }

    /**
     * Sends the updated shaman look.
     * @param player The player's instance.
     */
    private void sendShopShamanLookChange(GamePlayer player) {
        player.broadcastPacket(new PacketSendShamanLookChange(player.getPlayerData().shamanLook));
    }

    /**
     * The item's type.
     */
    enum BuyResult {
        REGULAR_ITEM,
        SHAMAN_ITEM,
        EMOJI,
        BANNER
    }
}