package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.database.DBManager;
import org.transformice.database.collections.Account;
import org.transformice.libraries.Pair;
import org.transformice.packets.send.legacy.player.C_PlayerUnlockTitle;
import org.transformice.properties.configs.shop.PromotionsConfig;
import org.transformice.properties.configs.shop.ShopItemConfig;
import org.transformice.properties.configs.shop.ShopOutfitsConfig;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;
import org.transformice.packets.send.informations.C_ShopTimestamp;
import org.transformice.packets.send.informations.C_TranslationMessage;
import org.transformice.packets.send.newpackets.C_LoadItemSprites;
import org.transformice.packets.send.newpackets.C_LoadShamanSprites;
import org.transformice.packets.send.newpackets.C_OpenFashionSquadOutfitsWindow;
import org.transformice.packets.send.newpackets.C_OpenFashionSquadSalesWindow;
import org.transformice.packets.send.newpackets.C_PurchasedEmojis;
import org.transformice.packets.send.transformice.C_OpenDressingWindow;
import org.transformice.packets.send.player.C_ShopOpen;
import org.transformice.packets.send.shop.*;

public final class ParseShop {
    private final Client client;
    private final Server server;

    /**
     * Creates a new shop.
     * @param client The player.
     */
    public ParseShop(Client client) {
        this.client = client;
        this.server = client.getServer();
    }

    /**
     * Opens the shop.
     * @param sendShopItems Is showing the server shop items.
     */
    public void sendOpenShop(boolean sendShopItems) {
        int cheeses = this.client.getAccount().getShopCheeses();
        int strawberries = this.client.getAccount().getShopStrawberries();
        String playerLook = this.client.getAccount().getMouseLook();
        String shamanLook = this.client.getAccount().getShamanLook();
        List<String> purchasedClothes = this.client.getAccount().getShopClothes();
        List<Integer> purchasedEmojis = this.client.getAccount().getPurchasedEmojis();
        Map<Integer, String> purchasedShamanItems = this.client.getAccount().getShopShamanItems();
        Map<Integer, String> purchasedItems = this.client.getAccount().getShopItems();
        List<Integer> favoritedItems = this.client.getAccount().getFavoritedItems();

        this.client.sendPacket(new C_ShopOpen(cheeses, strawberries, playerLook, shamanLook, sendShopItems, purchasedClothes, purchasedShamanItems, purchasedEmojis, purchasedItems, favoritedItems));
    }

    /**
     * Buys a clothe the shop.
     * @param clotheID The clothe id.
     * @param usingStrawberries Is using strawberries for the purchase.
     */
    public void buyShopClothe(int clotheID, boolean usingStrawberries) {
        int cheese_amount;
        int strawberries_amount = switch (clotheID) {
            case 0 -> {
                cheese_amount = 40;
                yield 5;
            }
            case 1 -> {
                cheese_amount = 1000;
                yield 50;
            }
            case 2 -> {
                cheese_amount = 2000;
                yield 100;
            }
            default -> {
                cheese_amount = 4000;
                yield 100;
            }
        };

        if(usingStrawberries) {
            int strawBerries = this.client.getAccount().getShopStrawberries();
            if(strawBerries - strawberries_amount < 0) {
                return;
            }
            this.client.getAccount().setShopStrawberries(strawBerries - strawberries_amount);
        } else {
            int cheeses = this.client.getAccount().getShopCheeses();
            if(cheeses - cheese_amount < 0) {
                return;
            }
            this.client.getAccount().setShopCheeses(cheeses - cheese_amount);
        }

        String info = String.format("%02d/1;0,0,0,0,0,0,0,0,0,0,0,0/%s/%s", clotheID, String.format("%06X", this.client.getAccount().getMouseColor() & 0xFFFFFF), String.format("%06X", this.client.getAccount().getShamanColor() & 0xFFFFFF));
        this.client.getAccount().getShopClothes().add(info);
        this.sendOpenShop(false);
    }

    /**
     * Buys an emoji from the shop.
     * @param emoji_id The emoji id.
     * @param usingStrawberries Is using strawberries for the purchase.
     */
    public void buyShopEmoji(int emoji_id, boolean usingStrawberries) {
        if(!Application.getShopEmojiInfo().containsKey(emoji_id)) {
            Application.getLogger().warn(Application.getTranslationManager().get("shopemojinotofound", emoji_id));
            return;
        }

        var info = Application.getShopEmojiInfo().get(emoji_id);
        if(usingStrawberries) {
            int strawBerries = this.client.getAccount().getShopStrawberries();
            if(strawBerries - info.strawberry_price < 0) {
                return;
            }
            this.client.getAccount().setShopStrawberries(strawBerries - info.strawberry_price);
        } else {
            int shopCheeses = this.client.getAccount().getShopCheeses();
            if(shopCheeses - info.cheese_price < 0) {
                return;
            }
            this.client.getAccount().setShopCheeses(shopCheeses - info.cheese_price);
        }

        this.sendBuyResult(emoji_id, 3);
        this.client.getAccount().getPurchasedEmojis().add(emoji_id);
        this.sendOpenShop(true);
        this.client.sendPacket(new C_PurchasedEmojis(this.client.getAccount().getPurchasedEmojis()));
    }

    /**
     * Buys a regular item from the shop.
     * @param item_id The item id.
     * @param usingStrawberries Is using strawberries for the purchase.
     * @param item_price The item price.
     */
    public void buyShopItem(int item_id, boolean usingStrawberries, int item_price) {
        Pair<Integer, Integer> shopInfo = this.getShopItemInfo(item_id);
        ShopItemConfig.ShopItem info = Application.getShopItemInfo().get(shopInfo.getFirst() + "_" + shopInfo.getSecond());
        if (info == null || info.type == 1) {
            return;
        }

        if(usingStrawberries) {
            int price = this.getPromotionPrice(item_id, true, info.strawberry_price);
            if(price != item_price) return;

            int strawBerries = this.client.getAccount().getShopStrawberries();
            if(strawBerries - price < 0) {
                return;
            }
            this.client.getAccount().setShopStrawberries(strawBerries - price);
        } else {
            int shopCheeses = this.client.getAccount().getShopCheeses();
            if(shopCheeses - info.cheese_price < 0) {
                return;
            }
            this.client.getAccount().setShopCheeses(shopCheeses - info.cheese_price);
        }

        this.client.getAccount().getShopItems().put(item_id, "");
        this.sendBuyResult(item_id, 1);
        this.client.sendPacket(new C_PurchaseItem(item_id));
        this.sendOpenShop(false);
    }

    /**
     * Buys a customization for regular item from the shop.
     * @param item_id The regular item id.
     * @param usingStrawberries Is using strawberries for the purchase.
     */
    public void buyShopItemCustom(int item_id, boolean usingStrawberries) {
        Pair<Integer, Integer> shopInfo = this.getShopItemInfo(item_id);
        if(Application.getShopItemInfo().get(shopInfo.getFirst() + "_" + shopInfo.getSecond()) == null) return;
        if(usingStrawberries) {
            int strawberries = this.client.getAccount().getShopStrawberries();
            if(strawberries - 150 < 0) {
                return;
            }
            this.client.getAccount().setShopStrawberries(strawberries - 150);
        } else {
            int cheeses = this.client.getAccount().getShopCheeses();
            if(cheeses - 4000 < 0) {
                return;
            }
            this.client.getAccount().setShopCheeses(cheeses - 4000);
        }

        this.client.getAccount().getShopShamanItems().put(item_id, item_id + "_");
        this.sendOpenShop(false);
    }

    /**
     * Buys a shaman item from the shop.
     * @param item_id The shaman item id.
     * @param usingStrawberries Is using strawberries for the purchase.
     */
    public void buyShopShamanItem(int item_id, boolean usingStrawberries) {
        var info = Application.getShopShamanItemInfo().get(item_id);
        if (info == null || info.type == 1) {
            Application.getLogger().warn(Application.getTranslationManager().get("shopshamanitemnotfound", item_id));
            return;
        }

        if(usingStrawberries) {
            int price = this.getPromotionPrice(item_id, false, info.strawberry_price);
            int strawBerries = this.client.getAccount().getShopStrawberries();
            if(strawBerries - price < 0) {
                return;
            }
            this.client.getAccount().setShopStrawberries(strawBerries - price);
        } else {
            int shopCheeses = this.client.getAccount().getShopCheeses();
            if(shopCheeses - info.cheese_price < 0) {
                return;
            }
            this.client.getAccount().setShopCheeses(shopCheeses - info.cheese_price);
        }

        this.client.getAccount().getShopShamanItems().put(item_id, "");
        this.sendBuyResult(item_id, 2);
        this.sendOpenShop(false);
    }

    /**
     * Buys a customization for shaman item from the shop.
     * @param item_id The shaman item id.
     * @param usingStrawberries Is using strawberries for the purchase.
     */
    public void buyShopShamanItemCustom(int item_id, boolean usingStrawberries) {
        if(Application.getShopShamanItemInfo().get(item_id) == null) return;
        if(usingStrawberries) {
            int strawberries = this.client.getAccount().getShopStrawberries();
            if(strawberries - 150 < 0) {
                return;
            }
            this.client.getAccount().setShopStrawberries(strawberries - 150);
        } else {
            int cheeses = this.client.getAccount().getShopCheeses();
            if(cheeses - 4000 < 0) {
                return;
            }
            this.client.getAccount().setShopCheeses(cheeses - 4000);
        }

        this.client.getAccount().getShopShamanItems().put(item_id, item_id + "_");
        this.sendOpenShop(false);
    }

    /**
     * Changes the color of the regular item in the shop.
     * @param item_id The shaman item id.
     */
    public void customizeItem(int item_id, ArrayList<Integer> customs) {
        Pair<Integer, Integer> shopInfo = this.getShopItemInfo(item_id);
        if(Application.getShopItemInfo().get(shopInfo.getFirst() + "_" + shopInfo.getSecond()) == null) return;

        String[] newCustoms = new String[customs.size()];
        for (int i = 0; i < customs.size(); i++) {
            newCustoms[i] = String.format("%06X", 0xFFFFFF & customs.get(i));
        }

        this.client.getAccount().getShopItems().put(item_id, item_id + "_" + String.join("+", newCustoms));
        this.equipItem(item_id);
    }

    /**
     * Changes the color of the shaman item in the shop.
     * @param item_id The shaman item id.
     */
    public void customizeShamanItem(int item_id, ArrayList<Integer> customs) {
        if(Application.getShopShamanItemInfo().get(item_id) == null) return;

        String[] newCustoms = new String[customs.size()];
        for (int i = 0; i < customs.size(); i++) {
            newCustoms[i] = String.format("%06X", 0xFFFFFF & customs.get(i));
        }

        this.client.getAccount().getShopShamanItems().put(item_id, item_id + "_" + String.join("+", newCustoms));
        this.equipShamanItem(item_id);
    }

    /**
     * Equips a cloth.
     * @param clotheId The clothe id.
     */
    public void equipClothe(int clotheId) {
        for(String clothe : this.client.getAccount().getShopClothes()) {
            String[] parts = clothe.split("/");
            if(parts[0].equals(String.format("%02d", clotheId))) {
                this.client.getAccount().setMouseLook(parts[1]);
                break;
            }
        }

        this.sendOpenShop(false);
        this.sendShopLookChange();

    }

    /**
     * Equips a regular item.
     * @param item_id The item id.
     */
    @SuppressWarnings("all")
    public void equipItem(int item_id) {
        if(Application.getShopItemInfo().get(this.getShopItemInfo(item_id).getFirst() + "_" + this.getShopItemInfo(item_id).getSecond()) == null || this.client.getAccount().getShopItems().get(item_id) == null) return;

        int category = this.getLookPosition(item_id, true);
        if(category == -1) return;

        if(category == 23) {
            // special for furs
            String mouseLook = this.client.getAccount().getMouseLook();
            String furId = String.valueOf(this.getShopItemInfo(item_id).getSecond());
            if(mouseLook.startsWith(furId)) {
                mouseLook = mouseLook.replace(furId, "1");
                this.client.getAccount().setMouseColor(7886906);
            } else {
                mouseLook = mouseLook.replaceFirst("^\\d+", furId);
                switch (furId) {
                    case "0":
                        this.client.getAccount().setMouseColor(12423271);
                        break;
                    case "1":
                        this.client.getAccount().setMouseColor(5846552);
                        break;
                    case "2":
                        this.client.getAccount().setMouseColor(9209983);
                        break;
                    case "3":
                        this.client.getAccount().setMouseColor(14670030);
                        break;
                    case "4":
                        this.client.getAccount().setMouseColor(5129274);
                        break;
                    case "5":
                        this.client.getAccount().setMouseColor(14925950);
                        break;
                    case "6":
                        this.client.getAccount().setMouseColor(2564640);
                        break;
                    case "7":
                        this.client.getAccount().setMouseColor(7886906);
                        break;
                    default:
                        this.client.getAccount().setMouseColor(7886906);
                }
            }

            this.client.getAccount().setMouseLook(mouseLook);
            this.sendOpenShop(false);
            this.sendShopLookChange();
            return;
        }

        String[] lookItems = this.client.getAccount().getMouseLook().split(";", 2)[1].split(",");
        String custom = this.client.getAccount().getShopItems().get(item_id);
        String value = (custom.isEmpty()) ? String.valueOf(this.getShopItemInfo(item_id).getSecond()) : custom;
        if (value.endsWith("_")) {
            value = value.substring(0, value.length() - 1);
        }

        if (lookItems[category].equals(value)) {
            lookItems[category] = "0";
        } else {
            lookItems[category] = value;
        }

        this.client.getAccount().setMouseLook(this.client.getAccount().getMouseLook().split(";", 2)[0] + ';' + String.join(",", lookItems));
        this.sendOpenShop(false);
        this.sendShopLookChange();
    }

    /**
     * Equips a shaman item.
     * @param item_id The shaman item id.
     */
    @SuppressWarnings("all")
    public void equipShamanItem(int item_id) {
        if(Application.getShopShamanItemInfo().get(item_id) == null || this.client.getAccount().getShopShamanItems().get(item_id) == null) return;

        String[] lookItems = this.client.getAccount().getShamanLook().split(",");
        String custom = this.client.getAccount().getShopShamanItems().get(item_id);
        int category = this.getLookPosition(item_id, false);

        String value = (custom.isEmpty()) ? String.valueOf(item_id) : custom;
        if (value.endsWith("_")) {
            value = value.substring(0, value.length() - 1);
        }

        if (lookItems[category].equals(value)) {
            lookItems[category] = "0";
        } else {
            lookItems[category] = value;
        }

        this.client.getAccount().setShamanLook(String.join(",", lookItems));
        this.sendOpenShop(false);
        this.sendShopShamanLookChange();
    }

    /**
     * Creates a new outfit in the shop.
     * @param outfitName The outfit name.
     * @param background The outfit background.
     * @param timestamp The date when it will expire.
     * @param outfitLook The outfit look.
     */
    public void sendAddOutfit(String outfitName, int background, String timestamp, String outfitLook) {

        long id = DBManager.getCounterValue("lastOutfitId");

        long tmp;
        try {
            tmp = Long.parseLong(timestamp);
        } catch (Exception ignored) {
            tmp = Utils.getUnixTime();
        }

        if(tmp < Utils.getUnixTime()) {
            this.client.sendPacket(new C_ServerMessage(true, "The timestamp must be a valid."));
            return;
        }

        ShopOutfitsConfig.ShopOutfit myShopOutfit = new ShopOutfitsConfig.ShopOutfit(outfitName, outfitLook, background, this.client.getPlayerName(), tmp, false);
        Application.getShopOutfitsInfo().put((int)id, myShopOutfit);

        this.sendOpenFashionSquadOutfitsWindow();
    }

    /**
     * Creates a new promotion (sale) in the shop.
     * @param promotionItemId The promotion item id.
     * @param startDate The promotion start date. (timestamp)
     * @param endDate The promotion end date. (timestamp).
     * @param percentage The promotion percentage.
     */
    public void sendAddPromotion(String promotionItemId, String startDate, String endDate, int percentage) {
        long id = DBManager.getCounterValue("lastSaleId");

        long startDateTmp;
        try {
            startDateTmp = Long.parseLong(startDate);
        } catch (Exception ignored) {
            startDateTmp = Utils.getUnixTime();
        }

        long endDateTmp;
        try {
            endDateTmp = Long.parseLong(endDate);
        } catch (Exception ignored) {
            endDateTmp = Utils.getUnixTime();
        }

        if(startDateTmp < Utils.getUnixTime() || startDateTmp > endDateTmp) {
            this.client.sendPacket(new C_ServerMessage(true, "The timestamp must be a valid."));
            return;
        }

        PromotionsConfig.Promotion myPromotion = new PromotionsConfig.Promotion(promotionItemId, startDateTmp, endDateTmp, percentage, this.client.getPlayerName(), false, true, promotionItemId.contains("_"));
        Application.getPromotionsInfo().put((int)id, myPromotion);
    }

    /**
     * Opens the dressing window.
     */
    public void sendOpenDressingWindow() {
        this.client.sendPacket(new C_OpenDressingWindow(Application.getShopItemInfo(), Application.getPromotionsInfo()));
    }

    /**
     * Opens the Fashion Squad - Outfits window with all shop outfits.
     */
    public void sendOpenFashionSquadOutfitsWindow() {
        this.client.sendPacket(new C_OpenFashionSquadOutfitsWindow(Application.getShopOutfitsInfo()));
    }

    /**
     * Opens the Fashion Squad - Sales window with all shop promotions.
     */
    public void sendOpenFashionSquadSalesWindow() {
        this.client.sendPacket(new C_OpenFashionSquadSalesWindow(Application.getPromotionsInfo()));
    }

    /**
     * Removes an outfit from the shop.
     * @param id The outfit id.
     */
    public void sendRemoveOutfit(int id) {
        Application.getShopOutfitsInfo().remove(id);
        this.sendOpenFashionSquadOutfitsWindow();
    }

    /**
     * Removes a promotion from the shop.
     * @param id The promotion id.
     */
    public void sendRemovePromotion(int id) {
        Application.getPromotionsInfo().remove(id);
        this.sendOpenFashionSquadSalesWindow();
    }

    /**
     * Sends the shop currency (Cheeses & Strawberries/Fraises).
     */
    public void sendShopCurrency() {
        this.client.sendPacket(new C_ShopCurrency(this.client.getAccount().getShopCheeses(), this.client.getAccount().getShopStrawberries()));
    }

    /**
     * Sends the customized shaman items.
     */
    public void sendShopCustomizedShamanItems() {
        this.client.sendPacket(new C_ShamanItems(this.client.getAccount().getShopShamanItems(), this.client.getAccount().getShamanLook()));
    }

    /**
     * Sends a gift containing a shop item.
     * @param playerName The player name aka receiver.
     * @param is_shaman_item Is shaman item.
     * @param itemId The item id.
     * @param message Gift message.
     */
    public void sendShopGift(String playerName, boolean is_shaman_item, int itemId, String message) {
        if(this.client.isGuest()) return;

        Account receiverAcc = this.server.getPlayerAccount(playerName);
        if(receiverAcc == null) {
            this.client.sendPacket(new C_ShopGiftResult(1, playerName));
            return;
        }

        if((receiverAcc.getShopShamanItems().containsKey(itemId) && is_shaman_item) || receiverAcc.getShopItems().containsKey(itemId)) {
            this.client.sendPacket(new C_ShopGiftResult(2, playerName));
            return;
        }

        if(is_shaman_item) {
            var info = Application.getShopShamanItemInfo().get(itemId);
            this.client.getAccount().setShopStrawberries(this.client.getAccount().getShopStrawberries() - this.getPromotionPrice(itemId, false, info.strawberry_price));
        } else {
            Pair<Integer, Integer> shopInfo = this.getShopItemInfo(itemId);
            if(Application.getShopItemInfo().get(shopInfo.getFirst() + "_" + shopInfo.getSecond()) == null) return;
            var info = Application.getShopItemInfo().get(shopInfo.getFirst() + "_" + shopInfo.getSecond());
            this.client.getAccount().setShopStrawberries(this.client.getAccount().getShopStrawberries() - this.getPromotionPrice(itemId, true, info.strawberry_price));
        }

        if(this.server.checkIsConnected(playerName)) {
            this.server.lastGiftID++;
            this.server.getPlayers().get(playerName).sendPacket(new C_ShopGift(this.server.lastGiftID, this.client.getPlayerName(), this.client.getAccount().getMouseLook(), is_shaman_item, itemId, message, false));
        } else {
            receiverAcc.getShopGifts().add(this.server.lastGiftID + "|" + this.client.getPlayerName() + "|" + this.client.getAccount().getMouseLook() + "|" + is_shaman_item + "|" + itemId + "|" + message);
            receiverAcc.save();
        }
        this.server.getShopGifts().put(this.server.lastGiftID, new Object[] {this.client.getPlayerName(), is_shaman_item, itemId});
        this.client.sendPacket(new C_ShopGiftResult(0, playerName));
        this.sendOpenShop(false);
    }

    /**
     * Sends the result after opening the shop gift.
     * @param giftId The gift id.
     * @param isOpen Is opened.
     * @param message The gift message.
     * @param isMessage Is sent the message to sender.
     */
    public void sendShopGiftResult(int giftId, boolean isOpen, String message, boolean isMessage) {
        if(this.client.isGuest()) return;
        Object[] values = this.server.getShopGifts().get(giftId);
        Client player = this.server.getPlayers().get(values[0]);

        if(isOpen) {
            if (player != null) {
                player.sendPacket(new C_TranslationMessage("", "$DonItemRecu", new String[]{this.client.getPlayerName()}));
            }

            boolean isShamanItem = (boolean) values[1];
            int fullItem = (int) values[2];

            if(isShamanItem) {
                this.client.getAccount().getShopShamanItems().put(fullItem, "");
                this.sendBuyResult(fullItem, 2);
            } else {
                this.client.getAccount().getShopItems().put(fullItem, "");
            }
        }

        if (!message.isEmpty()) {
            if(player != null) {
                player.sendPacket(new C_ShopGift(giftId, this.client.getPlayerName(), this.client.getAccount().getMouseLook(), (boolean)values[1], (int)values[2], message, true));
            } else {
                this.server.getPlayerAccount((String)values[0]).getShopMessages().add("0|" + this.client.getPlayerName() + "|" + this.client.getAccount().getMouseLook() + "|" + values[1] + "|" + values[2] + "|" + message);
                this.server.getPlayerAccount((String)values[0]).save();
            }
        }
    }

    /**
     * Sends the shop gifts.
     */
    public void sendShopLoginGifts() {
        for(String info : this.client.getAccount().getShopGifts()) {
            String[] parts = info.split("\\|");
            String message = "";
            if(parts.length > 5) {
                message = parts[5];
            }

            this.client.sendPacket(new C_ShopGift(Integer.parseInt(parts[0]), parts[1], parts[2], Boolean.parseBoolean(parts[3]), Integer.parseInt(parts[4]), message, false));
        }

        for(String info : this.client.getAccount().getShopMessages()) {
            String[] parts = info.split("\\|");
            this.client.sendPacket(new C_ShopGift(0, parts[0], parts[1], Boolean.parseBoolean(parts[2]), Integer.parseInt(parts[3]), parts[4], true));
        }

        this.client.getAccount().getShopGifts().clear();
        this.client.getAccount().getShopMessages().clear();
    }

    /**
     * Sends the shop sales and promotions.
     */
    public void sendShopPromotions() {
        for(var promotion : Application.getPromotionsInfo().values()) {
            int itemId;
            if(promotion.is_regular_item) {
                String[] parts = promotion.item_id.split("_");
                if(parts[0].equals("23")) {
                    itemId = Integer.parseInt(parts[0] + "0" + parts[1]);
                } else {
                    itemId = Integer.parseInt(promotion.item_id.replace("_", ""));
                }
            } else {
                itemId = Integer.parseInt(promotion.item_id);
            }

            this.client.sendPacket(new C_ShopSpecialOffer(promotion.is_sale, promotion.is_regular_item, itemId, (promotion.promotion_end_date - Utils.getUnixTime() > 0), promotion.promotion_end_date, promotion.promotion_percentage));
        }
    }

    /**
     * Sends the shop's external resource (sprite) ids.
     */
    public void sendShopSprites() {
        this.client.sendPacket(new C_LoadShamanSprites(Application.getPropertiesInfo().shaman_sprites));
        this.client.sendPacket(new C_LoadItemSprites(Application.getPropertiesInfo().shop_sprites));
    }

    /**
     * Sends the time in the shop.
     */
    public void sendShopTime() {
        this.client.sendPacket(new C_ShopTimestamp());
    }

    /**
     * Saves a cloth.
     * @param clotheId The clothe id.
     */
    public void saveClothe(int clotheId) {
        for(String clothe : this.client.getAccount().getShopClothes()) {
            String[] parts = clothe.split("/");
            if(parts[0].equals(String.format("%02d", clotheId))) {
                parts[1] = this.client.getAccount().getMouseLook();
                parts[2] = String.format("%06X", this.client.getAccount().getMouseColor() & 0xFFFFFF);
                parts[3] = String.format("%06X", this.client.getAccount().getShamanColor() & 0xFFFFFF);

                this.client.getAccount().getShopClothes().set(clotheId, String.join("/", parts));
                break;
            }
        }

        this.sendOpenShop(false);
    }

    /**
     * Gets the position to write the item id.
     * @param item_id The full item id.
     * @param is_regular_item Is shop item or shop shaman item.
     * @return The item category.
     */
    private int getLookPosition(int item_id, boolean is_regular_item) {
        if(!is_regular_item) {
            return switch (item_id / 100) {
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
        } else {
            if((item_id > 0 && item_id <= 99) || (item_id >= 10000 && item_id <= 19999)) return 0; // head
            if(item_id >= 100 && item_id <= 199 || (item_id >= 20000 && item_id <= 29999)) return 1; // eyes
            if(item_id >= 200 && item_id <= 299 || (item_id >= 30000 && item_id <= 39999)) return 2; // ears
            if(item_id >= 300 && item_id <= 399 || (item_id >= 40000 && item_id <= 49999)) return 3; // mouth
            if(item_id >= 400 && item_id <= 499 || (item_id >= 50000 && item_id <= 59999)) return 4; // neck
            if(item_id >= 500 && item_id <= 599 || (item_id >= 60000 && item_id <= 69999)) return 5; // hairstyle
            if(item_id >= 600 && item_id <= 699 || (item_id >= 70000 && item_id <= 79999)) return 6; // tail
            if(item_id >= 700 && item_id <= 799 || (item_id >= 80000 && item_id <= 89999)) return 7; // contact leans
            if(item_id >= 800 && item_id <= 899 || (item_id >= 90000 && item_id <= 99999)) return 8; // hands
            if(item_id >= 1100 && item_id <= 1199) return 11; // tattoos
            if(item_id >= 2000 && item_id <= 2999 || (item_id >= 230000 && item_id <= 239999)) return 23;
            return -1;
        }
    }

    /**
     * Gets the promotion price.
     * @param item_id The item id.
     * @param is_regular_item Is shop item or shaman item.
     * @param price The start price.
     * @return The promotion price if promotion exist or the normal price.
     */
    private int getPromotionPrice(Integer item_id, boolean is_regular_item, int price) {
        PromotionsConfig.Promotion myPromotion = null;
        for(var info : Application.getPromotionsInfo().values()) {
            String itemId;
            if(!is_regular_item) {
                itemId = String.valueOf(item_id);
            } else {
                Pair<Integer, Integer> shopInfo = this.getShopItemInfo(item_id);
                itemId = shopInfo.getFirst() + "_" + shopInfo.getSecond();
            }

            if(itemId.startsWith("22")) { // furs
                itemId = "23" + itemId.substring(2);
            }

            if(info.item_id.equals(itemId)) {
                myPromotion = info;
                break;
            }
        }

        if(myPromotion == null) return price;
        return (int)(myPromotion.promotion_percentage / 100.0 * price);
    }

    /**
     * Gets the item category and item id from full item.
     * @param fullItemId The full item.
     * @return Pair that contains the category and id of the item.
     */
    private Pair<Integer, Integer> getShopItemInfo(int fullItemId) {
        int itemCat = fullItemId > 9999 ? ((fullItemId - 10000) / 10000) : fullItemId / 100;
        int itemId = fullItemId > 9999 ? fullItemId % 1000 : fullItemId > 999 ? fullItemId % 100 : fullItemId > 99 ? fullItemId % (100 * itemCat) : fullItemId;
        return new Pair<>(itemCat, itemId);
    }

    /**
     * Sends the updated look in the shop.
     */
    private void sendShopLookChange() {
        this.client.sendPacket(new C_ShopMouseLook(this.client.getAccount().getMouseLook(), this.client.getAccount().getMouseColor()));
    }

    /**
     * Sends the updated look.
     */
    private void sendShopShamanLookChange() {
        this.client.sendPacket(new C_ShopShamanLook(this.client.getAccount().getShamanLook()));
    }


    private void sendBuyResult(int item_id, int item_type) {
        this.client.getParseDailyQuestsInstance().sendMissionIncrease(6);

        /// 1 - regular item
        /// 2 - shaman item
        /// 3 - emoji

        for (Map.Entry<Integer, Double> entry : this.server.shopTitleList.entrySet()) {
            int needResources = entry.getKey();
            double titleIntegerID = entry.getValue();
            if (this.client.getAccount().getShopItems().size() >= needResources && !this.client.getAccount().getTitleList().contains(titleIntegerID)) {
                int decPart = (int) Math.round((titleIntegerID - (int) titleIntegerID) * 10);
                for(int i = 0; i < decPart; i++) {
                    this.client.getAccount().getTitleList().remove((int)titleIntegerID + (i / 10.0));
                }

                this.client.getRoom().sendAllOld(new C_PlayerUnlockTitle(this.client.getSessionId(), (int)titleIntegerID, decPart));
                this.client.getAccount().getTitleList().add(titleIntegerID);
            }
        }
        /// TODO: Badges for fur items and animation after receive the badge.
    }
}