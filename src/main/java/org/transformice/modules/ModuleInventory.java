package org.transformice.modules;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.psframework.utils.PSBase64;
import org.psframework.utils.PSRandom;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.config.VillageNPCConfig;
import org.transformice.config.inventory.ConsumableConfig;
import org.transformice.data.objects.PlayerTitleInfo;
import org.transformice.data.objects.TradeResultType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.modules.impl.ModulePlayer;

// Packets
import org.transformice.network.NetByteArray;
import org.transformice.packets.send.informations.PacketSendPlayerSendLetter;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;
import org.transformice.packets.send.inventory.*;
import org.transformice.packets.send.login.PacketSendOpenNpcShop;
import org.transformice.packets.send.player.PacketSendCurrencyAwardPopup;
import org.transformice.packets.send.player.PacketSendPlayerAction;
import org.transformice.packets.send.player.PacketSendPlayerPet;
import org.transformice.packets.send.player.PacketSendPlayerRaiseItem;
import org.transformice.packets.send.player.PacketSendSaveWallpaper;
import org.transformice.packets.send.room.PacketSendCampfireSkill;
import org.transformice.packets.send.room.PacketSendLaunchHotAirBalloonWithBadge;
import org.transformice.packets.send.room.PacketSendVisualizeConsumable;
import org.transformice.packets.send.trade.*;
import org.transformice.utils.Utils;

public final class ModuleInventory extends ModulePlayer {
    private final Map<PlayerEntity.InventoryItem, ConsumableConfig> currentInventory;

    /**
     * Creates a inventory module.
     *
     * @param server The game server.
     * @param player The game player.
     */
    public ModuleInventory(GameServer server, GamePlayer player) {
        super(server, player);
        this.currentInventory = new HashMap<>();
    }

    /**
     * Initializes the player's inventory.
     */
    public void sendLoginInventoryInitialization() {
        if (this.player.getIsGuest())
            return;

        Map<Integer, PlayerEntity.InventoryItem> inventory = this.player.getPlayerData().getInventory();
        for (PlayerEntity.InventoryItem item : inventory.values()) {
            ConsumableConfig config = this.server.getInventoryConfig().get(item.itemId);
            if (config == null) {
                this.server.getLogger().warn("[Inventory] Unknown inventory id: {}, please add it to the inventory config.", item.itemId);
                continue;
            }

            this.currentInventory.put(item, config);
        }

        for (int itemId : new ArrayList<>(this.player.getPlayerData().getEquippedConsumables()))
            this.sendEquipConsumable(itemId, true);

        this.sendLoadInventory();
        if (!this.player.getPlayerData().getLetters().isEmpty()) {
            for (String letter : this.player.getPlayerData().getLetters()) {
                String[] info = letter.split("\\|");
                this.player.broadcastPacket(new PacketSendPlayerSendLetter(info[0], info[1], Byte.parseByte(info[2]), PSBase64.decode(info[3]).getBytes()));
            }

            this.player.getPlayerData().getLetters().clear();
        }
    }

    /**
     * Equip the consumable by id.
     *
     * @param consumableId The consumable id.
     * @param isEquip      Equip.
     */
    public void sendEquipConsumable(int consumableId, boolean isEquip) {
        List<Integer> equipped = this.player.getPlayerData().getEquippedConsumables();
        equipped.remove(Integer.valueOf(consumableId));
        if (!isEquip)
            return;

        if (!this.player.getPlayerData().getInventory().containsKey(consumableId))
            return;

        ConsumableConfig config = this.server.getInventoryConfig().get(consumableId);
        if (config == null || !config.getCanEquip())
            return;

        equipped.add(consumableId);
    }

    /**
     * Loads the player's inventory.
     */
    public void sendLoadInventory() {
        this.player.broadcastPacket(new PacketSendOpenInventory(this.currentInventory, this.player.getPlayerData().getEquippedConsumables()));
    }

    /**
     * Adds/Removes an item in the tribe.
     *
     * @param itemId The item id. (Consumable)
     * @param isAdd  Is adding or is removing.
     */

    public void sendTradeAddConsumable(int itemId, boolean isAdd, boolean isSeveral) {
        GamePlayer player = this.server.getPlayers().get(this.player.currentTradeName);
        if (player == null || !player.isOpenTrade)
            return;

        PlayerEntity.InventoryItem item = this.player.getPlayerData().getInventory().get(itemId);
        if (item == null)
            return;

        ConsumableConfig config = this.server.getInventoryConfig().get(itemId);
        if (config == null || !config.getCanTrade())
            return;

        Map<Integer, Integer> trade = this.player.getTradeConsumables();
        int count = trade.getOrDefault(itemId, 0);
        int quantity = isSeveral ? (isAdd ? item.itemAmount - count : count) : 1;
        if (quantity <= 0)
            return;

        if (isAdd) {
            if (count + quantity > item.itemAmount)
                return;

            trade.put(itemId, count + quantity);
        } else {
            if (count < quantity)
                return;

            if (count == quantity)
                trade.remove(itemId);
            else
                trade.put(itemId, count - quantity);
        }

        this.player.broadcastPacket(new PacketSendTradeAddItem(true, itemId, isAdd, quantity, item.getItemImage()));
        player.broadcastPacket(new PacketSendTradeAddItem(false, itemId, isAdd, quantity, item.getItemImage()));
    }

    /**
     * Terminates the current trade with the given player.
     *
     * @param playerName    The player name.
     * @param becauseShaman Because the trader is shaman.
     */
    public void sendTradeClose(String playerName, boolean becauseShaman) {
        if (!this.player.isOpenTrade)
            return;

        playerName = playerName.isEmpty() ? playerName : playerName.substring(0, 1).toUpperCase() + playerName.substring(1);
        GamePlayer player = this.server.getPlayers().get(playerName);
        if (player != null) {
            this.player.currentTradeName = "";
            this.player.isOpenTrade = false;
            this.player.getTradeConsumables().clear();
            this.player.isTradeConfirm = false;
            player.currentTradeName = "";
            player.isOpenTrade = false;
            player.getTradeConsumables().clear();
            player.isTradeConfirm = false;
            player.broadcastPacket(new PacketSendTradeResult(this.player.getPlayerName(), TradeResultType.TRADE_ENDED));
        }

        if (becauseShaman)
            this.player.broadcastPacket(new PacketSendTradeResult(playerName, TradeResultType.SHAMAN_CANNOT_TRADE));
    }

    /**
     * Sends the trade confirmation.
     *
     * @param isAccepted Is the trade accepted.
     */
    public void sendTradeConfirmation(boolean isAccepted) {
        GamePlayer player = this.server.getPlayers().get(this.player.currentTradeName);
        if (player == null || !player.isOpenTrade)
            return;

        this.player.isTradeConfirm = isAccepted;
        player.broadcastPacket(new PacketSendTradeLock(0, isAccepted));
        this.player.broadcastPacket(new PacketSendTradeLock(1, isAccepted));
        if (!isAccepted) {
            player.isTradeConfirm = false;
            this.player.broadcastPacket(new PacketSendTradeLock(2, false));
            player.broadcastPacket(new PacketSendTradeLock(2, false));
            return;
        }

        if (!player.isTradeConfirm)
            return;

        Map<Integer, Integer> myTrade = new HashMap<>(this.player.getTradeConsumables());
        Map<Integer, PlayerEntity.InventoryItem> myInventory = this.player.getPlayerData().getInventory();
        for (var entry : myTrade.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            PlayerEntity.InventoryItem item = myInventory.get(itemId);
            ConsumableConfig config = this.server.getInventoryConfig().get(itemId);
            if (item == null || config == null || !config.getCanTrade() || quantity <= 0 || quantity > item.itemAmount) {
                this.player.isTradeConfirm = false;
                this.player.broadcastPacket(new PacketSendTradeResult("", TradeResultType.INTERNAL_ERROR));
                player.isTradeConfirm = false;
                player.broadcastPacket(new PacketSendTradeResult("", TradeResultType.INTERNAL_ERROR));
                return;
            }
        }

        Map<Integer, Integer> theirTrade = new HashMap<>(player.getTradeConsumables());
        Map<Integer, PlayerEntity.InventoryItem> theirInventory = player.getPlayerData().getInventory();
        for (var entry : theirTrade.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            PlayerEntity.InventoryItem item = theirInventory.get(itemId);
            ConsumableConfig config = this.server.getInventoryConfig().get(itemId);
            if (item == null || config == null || !config.getCanTrade() || quantity <= 0 || quantity > item.itemAmount) {
                this.player.isTradeConfirm = false;
                this.player.broadcastPacket(new PacketSendTradeResult("", TradeResultType.INTERNAL_ERROR));
                player.isTradeConfirm = false;
                player.broadcastPacket(new PacketSendTradeResult("", TradeResultType.INTERNAL_ERROR));
                return;
            }
        }

        Map<Integer, String> myImages = new HashMap<>();
        Map<Integer, String> theirImages = new HashMap<>();
        for (int itemId : myTrade.keySet()) {
            String image = myInventory.get(itemId).getItemImage();
            myImages.put(itemId, image);
        }

        for (int itemId : theirTrade.keySet()) {
            String image = theirInventory.get(itemId).getItemImage();
            theirImages.put(itemId, image);
        }

        for (var entry : myTrade.entrySet())
            this.removeConsumable(entry.getKey(), entry.getValue());

        for (var entry : theirTrade.entrySet())
            player.getParseInventory().removeConsumable(entry.getKey(), entry.getValue());

        for (var entry : myTrade.entrySet()) {
            int itemId = entry.getKey();
            String image = myImages.get(itemId);
            String itemName = image == null || image.isEmpty() ? String.valueOf(itemId) : itemId + "_" + image;
            player.getParseInventory().addConsumable(itemName, entry.getValue(), false);
        }

        for (var entry : theirTrade.entrySet()) {
            int itemId = entry.getKey();
            String image = theirImages.get(itemId);
            String itemName = image == null || image.isEmpty() ? String.valueOf(itemId) : itemId + "_" + image;
            this.addConsumable(itemName, entry.getValue(), false);
        }

        this.player.currentTradeName = "";
        this.player.isOpenTrade = false;
        this.player.getTradeConsumables().clear();
        this.player.isTradeConfirm = false;
        player.currentTradeName = "";
        player.isOpenTrade = false;
        player.getTradeConsumables().clear();
        player.isTradeConfirm = false;
        this.player.broadcastPacket(new PacketSendTradeResult("", TradeResultType.TRADE_COMPLETED));
        this.player.broadcastPacket(new PacketSendTradeComplete());
        player.broadcastPacket(new PacketSendTradeResult("", TradeResultType.TRADE_COMPLETED));
        player.broadcastPacket(new PacketSendTradeComplete());
        this.sendLoadInventory();
        player.getParseInventory().sendLoadInventory();
    }

    /**
     * Creates a new trade between you and the given player name.
     *
     * @param playerName The player name.
     */
    public void sendTradeRequest(String playerName) {
        if (this.player.getIsGuest())
            return;

        playerName = playerName.isEmpty() ? playerName : playerName.substring(0, 1).toUpperCase() + playerName.substring(1);
        GamePlayer client = this.server.getPlayers().get(playerName);
        if (client == null || client.getIsGuest()) {
            this.player.broadcastPacket(new PacketSendTradeResult(playerName, TradeResultType.PLAYER_NOT_CONNECTED));
            return;
        }

        if (!client.isOpenTrade) {
            if (!client.getRoomName().equals(this.player.getRoomName())) {
                this.player.broadcastPacket(new PacketSendTradeResult(playerName, TradeResultType.DIFFERENT_ROOM));
                return;
            }

            if (!client.getIpAddress().equals(this.player.getIpAddress()) && !this.server.getConfig().getIsDebug()) {
                return;
            }

            if (client.isShaman) {
                this.player.broadcastPacket(new PacketSendTradeResult(playerName, TradeResultType.SHAMAN_CANNOT_TRADE));
                return;
            }

            this.player.broadcastPacket(new PacketSendTranslatedMessage("", "$Demande_Envoyée"));
            this.player.isOpenTrade = true;
            this.player.currentTradeName = playerName;
            client.broadcastPacket(new PacketSendTradeInvite(this.player.getSessionId()));
        } else {
            if (!client.currentTradeName.equals(this.player.getPlayerName())) {
                this.player.broadcastPacket(new PacketSendTradeResult(playerName, TradeResultType.ALREADY_IN_TRADE));
                return;
            }

            this.player.isOpenTrade = true;
            this.player.currentTradeName = playerName;
            this.player.broadcastPacket(new PacketSendTradeStart(client.getSessionId()));
            client.broadcastPacket(new PacketSendTradeStart(this.player.getSessionId()));
        }
    }

    /**
     * Saves the inventory database.
     */
    public void saveInventory() {
        if (this.player.getIsGuest())
            return;

        Map<Integer, PlayerEntity.InventoryItem> inventory = this.player.getPlayerData().getInventory();
        inventory.clear();
        for (PlayerEntity.InventoryItem item : this.currentInventory.keySet())
            inventory.put(item.itemId, item);
    }

    /**
     * Adds an item to current player.
     *
     * @param itemId   The item id.
     * @param quantity The item quantity.
     */
    public void addConsumable(String itemId, int quantity, boolean raise) {
        int index = itemId.indexOf('_');
        int consumableId = Short.parseShort(index == -1 ? itemId : itemId.substring(0, index));
        ConsumableConfig config = this.server.getInventoryConfig().get(consumableId);
        if (config == null) {
            this.server.getLogger().warn("[Inventory] Unknown inventory id: {}, please add it to the inventory config.", consumableId);
            return;
        }

        Map<Integer, PlayerEntity.InventoryItem> inventory = this.player.getPlayerData().getInventory();
        String image = index == -1 ? config.getImage() : itemId.substring(index + 1);
        PlayerEntity.InventoryItem item = inventory.computeIfAbsent(consumableId, i -> new PlayerEntity.InventoryItem(i, 0, image));
        this.currentInventory.putIfAbsent(item, config);
        quantity = Math.min(quantity, config.getLimit() - item.itemAmount);
        if (quantity <= 0)
            return;

        item.itemAmount += quantity;
        this.player.broadcastPacket(new PacketSendNewConsumableNotification(consumableId, quantity));
        this.player.broadcastPacket(new PacketSendUpdateInventory(consumableId, quantity));
        if (raise)
            this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerRaiseItem(this.player.getSessionId(), 4, consumableId));
    }

    /**
     * Gets the consumable amount.
     *
     * @param itemId The consumable's id.
     * @return The consumable amount.
     */
    public int getConsumableAmount(int itemId) {
        PlayerEntity.InventoryItem item = this.player.getPlayerData().getInventory().get(itemId);
        return item == null ? 0 : item.itemAmount;
    }

    /**
     * Removes an item from current player.
     *
     * @param itemId   The item id.
     * @param quantity The item quantity.
     */
    public void removeConsumable(int itemId, int quantity) {
        PlayerEntity.InventoryItem item = this.player.getPlayerData().getInventory().get(itemId);
        if (item == null || quantity <= 0)
            return;

        item.itemAmount -= Math.min(quantity, item.itemAmount);
        if (item.itemAmount == 0) {
            this.player.getPlayerData().getInventory().remove(itemId);
            this.currentInventory.remove(item);
            this.player.getPlayerData().getEquippedConsumables().remove(Integer.valueOf(itemId));
        }

        this.player.broadcastPacket(new PacketSendUpdateInventory(itemId, item.itemAmount));
    }

    /**
     * Handles the given consumable.
     *
     * @param consumableId The consumable id.
     */
    public void useInventoryItem(int consumableId) {
        ConsumableConfig config = this.server.getInventoryConfig().get(consumableId);
        if (config == null || !config.getCanUse() || (this.player.isDead && !config.getCanUseWhenDead()))
            return;

        if (this.getConsumableAmount(consumableId) <= 0)
            return;

        if (System.currentTimeMillis() - this.player.getPlayerRoom().getGameStartTimeMillis() < config.getCountdown() * 1000L) {
            this.player.broadcastPacket(new PacketSendTranslatedMessage("", "$DelaiConso", new String[]{String.valueOf(config.getCountdown())}));
            return;
        }

        boolean removeConsumable = true;
        switch (consumableId) {
            case 1, 5, 8, 20, 25, 26 -> {
                if (this.player.getPlayerRoom().disablePhysicalConsumables || this.player.getPlayerRoom().getParseLua().disablePhysicalConsumables)
                    return;

                int objId = switch (consumableId) {
                    case 1 -> 65;
                    case 5 -> 6;
                    case 8 -> 89;
                    case 20 -> 33;
                    case 25 -> 80;
                    default -> 95;
                };

                this.player.getPlayerRoom().sendAddShamanObject(this.player.getPlayerRoom().getNextObjectId(), objId, this.player.isFacingRight ? this.player.positionX + 28 : this.player.positionX - 28, this.player.isFacingRight ? this.player.positionY - 20 : this.player.positionY + 20, 0, this.player.isFacingRight ? 10 : -10, -3, true, true, this.player, true);
            }

            case 10 -> {
                for (GamePlayer client : this.player.getPlayerRoom().getPlayers().values()) {
                    if (client == this.player)
                        continue;

                    if (Math.abs(this.player.positionX - client.positionX) <= 100 && Math.abs(this.player.positionY - client.positionY) <= 100)
                        this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerAction(this.player.getSessionId(), 3, "", false));
                }
            }

            case 11 -> {
                if (this.player.getPlayerRoom().disablePhysicalConsumables || this.player.getPlayerRoom().getParseLua().disablePhysicalConsumables)
                    return;

                this.player.sendDeath();
                this.player.getPlayerRoom().sendAddShamanObject(this.player.getPlayerRoom().getNextObjectId(), 90, this.player.isFacingRight ? this.player.positionX + 28 : this.player.positionX - 28, this.player.isFacingRight ? this.player.positionY - 20 : this.player.positionY + 20, 0, this.player.isFacingRight ? 10 : -10, 0, true, true, this.player, true);
                this.player.getPlayerRoom().sendCreateConsumableTimer(this.player.getPlayerRoom().getLastObjectId());
            }

            case 21 ->
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerAction(this.player.getSessionId(), 12, "", false));

            case 24 -> {
                if (this.player.getPlayerRoom().disablePhysicalConsumables || this.player.getPlayerRoom().getParseLua().disablePhysicalConsumables)
                    return;

                this.player.getPlayerRoom().sendAddShamanObject(this.player.getPlayerRoom().getNextObjectId(), 63, this.player.isFacingRight ? this.player.positionX + 28 : this.player.positionX - 28, this.player.isFacingRight ? this.player.positionY + 20 : this.player.positionY - 20, 0, 0, 0, true, true, this.player, true);
            }

            case 28 ->
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendCampfireSkill(this.player.positionX, this.player.positionY, 15));

            case 2234 ->
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerAction(this.player.getSessionId(), 20, "", false));

            case 29, 30, 2241, 2330, 2351, 2522, 2576, 2581, 2591, 2609, 2612 -> removeConsumable = false;

            case 33 ->
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerAction(this.player.getSessionId(), 16, "", false));

            case 35 -> {
                var badges = this.player.getPlayerData().getPlayerBadges();
                removeConsumable = !badges.isEmpty();
                if (removeConsumable)
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendLaunchHotAirBalloonWithBadge(this.player.getSessionId(), badges.get(PSRandom.nextInt(0, badges.size() - 1))));
            }

            case 800 -> {
                this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerRaiseItem(this.player.getSessionId(), 2, 0));
                this.player.broadcastPacket(new PacketSendCurrencyAwardPopup(0, 1));
                this.player.getPlayerData().shopCheese++;
            }

            case 801 -> {
                this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerRaiseItem(this.player.getSessionId(), 2, 2));
                this.player.broadcastPacket(new PacketSendCurrencyAwardPopup(1, 1));
                this.player.getPlayerData().shopStrawberries++;
            }

            case 2239 ->
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendVisualizeConsumable(this.player.getSessionId(), 4, new Object[]{this.player.getPlayerData().shopCheese}));

            case 2246 ->
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerAction(this.player.getSessionId(), 24, "", false));

            case 2252, 2256, 2349, 2379, 2513, 2514, 2634, 2635 -> {
                this.player.drawColor = switch (consumableId) {
                    case 2252 -> 5687614;
                    case 2256 -> 13188682;
                    case 2349 -> 4308730;
                    case 2379 -> 16745472;
                    case 2513 -> 11354851;
                    case 2514 -> 15910958;
                    case 2634 -> 16777215;
                    default -> 0;
                };

                this.player.getPlayerRoom().broadcastPacket(new PacketSendVisualizeConsumable(this.player.getSessionId(), 1, new Object[]{500, this.player.drawColor}));
            }

            case 2250 -> {
                if (this.player.getPlayerRoom().disablePhysicalConsumables || this.player.getPlayerRoom().getParseLua().disablePhysicalConsumables)
                    return;

                this.player.getPlayerRoom().sendAddShamanObject(this.player.getPlayerRoom().getNextObjectId(), 97, this.player.isFacingRight ? this.player.positionX + 28 : this.player.positionX - 28, this.player.isFacingRight ? this.player.positionY - 20 : this.player.positionY + 20, 0, this.player.isFacingRight ? 10 : -10, -3, true, true, this.player, true);
            }

            case 2255 ->
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerRaiseItem(this.player.getSessionId(), 7, "$De6", PSRandom.nextInt(1, 6)));

            case 2259 -> {
                long time = this.player.getPlayerData().playedTime;
                this.player.getPlayerRoom().broadcastPacket(new PacketSendVisualizeConsumable(this.player.getSessionId(), 5, new Object[]{(int) (time / 86400), (int) ((time / 3600) % 24)}));
            }

            case 2578 -> {
                if (this.player.getPlayerRoom().disablePhysicalConsumables || this.player.getPlayerRoom().getParseLua().disablePhysicalConsumables)
                    return;

                this.player.getPlayerRoom().sendAddShamanObject(this.player.getPlayerRoom().getNextObjectId(), 7, this.player.isFacingRight ? this.player.positionX + 28 : this.player.positionX - 28, this.player.isFacingRight ? this.player.positionY - 20 : this.player.positionY + 20, 0, this.player.isFacingRight ? 10 : -10, 0, true, true, this.player, true);
                this.player.getPlayerRoom().sendCreateConsumableTimer(this.player.getPlayerRoom().getLastObjectId());
            }

            case 2579 -> {
                if (this.player.getPlayerRoom().disablePhysicalConsumables || this.player.getPlayerRoom().getParseLua().disablePhysicalConsumables)
                    return;

                this.player.getPlayerRoom().sendAddShamanObject(this.player.getPlayerRoom().getNextObjectId(), 2, this.player.isFacingRight ? this.player.positionX + 28 : this.player.positionX - 28, this.player.isFacingRight ? this.player.positionY - 20 : this.player.positionY + 20, 0, this.player.isFacingRight ? 10 : -10, 0, false, false, this.player, true);
                this.player.getPlayerRoom().sendCreateConsumableTimer(this.player.getPlayerRoom().getLastObjectId());
            }

            case 2582, 2616, 2619, 2623 -> {
                String fileName = switch (consumableId) {
                    case 2616 -> "elisah";
                    case 2619 -> "potager";
                    case 2623 -> "";
                    default -> "fishing";
                };

                this.player.broadcastPacket(new PacketSendSaveWallpaper(fileName));
            }

            case 31, 34, 2240, 2247, 2262, 2332, 2340, 2437, 2444, 2520, 2532, 2539, 2545, 2548, 2551, 2553, 2554, 2556, 2575, 2580, 2586, 2590, 2602, 2606, 2618, 2622, 2625, 2628, 2633 -> {
                if (this.player.getPlayerData().petInfo[0] != 0) {
                    removeConsumable = false;
                } else {
                    int itemId = switch (consumableId) {
                        case 31 -> 2;
                        case 34 -> 3;
                        case 2240 -> 4;
                        case 2247 -> 5;
                        case 2262 -> 6;
                        case 2332 -> 7;
                        case 2340 -> 8;
                        case 2437 -> 9;
                        case 2444 -> 10;
                        case 2520 -> 12;
                        case 2532 -> 13;
                        case 2539 -> 14;
                        case 2545 -> 15;
                        case 2548 -> 16;
                        case 2551 -> 17;
                        case 2553 -> 18;
                        case 2554 -> 19;
                        case 2556 -> 20;
                        case 2575 -> 21;
                        case 2580 -> 22;
                        case 2586 -> 23;
                        case 2590 -> 24;
                        case 2602 -> 25;
                        case 2606 -> 26;
                        case 2618 -> 27;
                        case 2622 -> 28;
                        case 2625 -> 29;
                        case 2628 -> 30;
                        default -> 31;
                    };

                    this.player.getPlayerData().petInfo = new long[]{itemId, Utils.getUnixTime() + 3600};
                    this.player.getPlayerRoom().broadcastPacket(new PacketSendPlayerPet(this.player.getSessionId(), itemId));
                }
            }
        }

        if (removeConsumable) {
            this.removeConsumable(consumableId, 1);
            this.player.getPlayerRoom().broadcastPacket(new PacketSendUseConsumable(this.player.getSessionId(), consumableId));
        } else {
            this.player.broadcastPacket(new PacketSendUseConsumable(this.player.getSessionId(), consumableId));
        }
    }

    /**
     * Handles when the player interacts with the village npcs.
     *
     * @param psData The packet data.
     */
    public void handleOfficialNPCFunctions(NetByteArray psData) {
        int functionId = psData.readByte();
        switch (functionId) {
            case 4:
                String npcName = psData.readUTF();
                this.player.currentOpenedNpc = npcName;
                this.player.broadcastPacket(new PacketSendOpenNpcShop(npcName, this.server.getVillageNPCConfig().get(npcName).getItems(), this.player, this.server.getInventoryConfig()));
                break;
            case 10:
                int itemPosition = psData.readByte();
                VillageNPCConfig.Item itemConfig = this.server.getVillageNPCConfig().get(this.player.currentOpenedNpc).getItems().get(itemPosition);
                if (this.player.getParseInventory().getConsumableAmount(itemConfig.getCostId()) < itemConfig.getCostQuantity())
                    return;

                if (this.isOwnItem(itemConfig.getType(), itemConfig.getItemId(), itemConfig.getCostQuantity(), this.server.getInventoryConfig().get(itemConfig.getItemId())) == 2)
                    return;

                this.player.getParseInventory().removeConsumable(itemConfig.getCostId(), itemConfig.getCostQuantity());
                switch (itemConfig.getType()) {
                    case 1:
                        if (!this.player.getPlayerData().getPlayerBadges().containsKey(itemConfig.getItemId()))
                            this.player.sendUnlockBadge(itemConfig.getItemId());
                        break;
                    case 2:
                        if (!this.player.getPlayerData().getShamanBadges().contains(itemConfig.getItemId())) {
                            this.player.getPlayerData().getShamanBadges().add(itemConfig.getItemId());
                            this.player.broadcastPacket(new PacketSendTranslatedMessage("", "$GainMacaron"));
                        }
                        break;
                    case 3:
                        if (!this.player.getPlayerData().getUnlockedTitles().contains(new PlayerTitleInfo((short) itemConfig.getItemId(), (byte) 1)))
                            this.player.sendUnlockTitle(itemConfig.getItemId());
                        break;
                    case 4:
                        this.player.getParseInventory().addConsumable(String.valueOf(itemConfig.getItemId()), itemConfig.getQuantity(), true);
                        break;
                    case 5:
                        /// TODO: this.server.getParseShop().sendAddShopItem(this.player, itemConfig.getItemId(), false);
                        break;
                    case 7:
                        if (!this.player.getPlayerData().getShopEmojis().contains(itemConfig.getItemId()))
                            this.player.getPlayerData().getShopEmojis().add(itemConfig.getItemId());
                        break;
                    case 8:
                        if (!this.player.getPlayerData().getShopAdventureBanners().contains(itemConfig.getItemId()))
                            this.player.getPlayerData().getShopAdventureBanners().add(itemConfig.getItemId());
                        break;
                }
                break;
        }
    }

    /**
     * Checks if the player has owned the item id.
     *
     * @param itemType The item's type.
     * @param itemId   The item's id.
     * @return 2 - If the player owned it or else 0 if can be purchased or else 1.
     */
    public int isOwnItem(int itemType, int itemId, int itemCostQuantity, ConsumableConfig itemConfig) {
        boolean isOwned = false;
        if (itemType == 1) {
            isOwned = this.player.getPlayerData().getPlayerBadges().containsKey(itemId);
        } else if (itemType == 2) {
            isOwned = this.player.getPlayerData().getShamanBadges().contains(itemId);
        } else if (itemType == 3) {
            for (PlayerTitleInfo titleInfo : this.player.getPlayerData().getUnlockedTitles()) {
                if (titleInfo.titleId == itemId) {
                    isOwned = true;
                    break;
                }
            }
        } else if (itemType == 4) {
            if (this.player.getParseInventory().getConsumableAmount(itemId) > itemConfig.getLimit())
                return 1;

            isOwned = this.player.getParseInventory().getConsumableAmount(itemId) > 0;
        } else if (itemType == 5) {
            isOwned = this.player.getPlayerData().getShopItems().containsKey(itemId);
        } else if (itemType == 7) {
            isOwned = this.player.getPlayerData().getShopEmojis().contains(itemId);
        } else if (itemType == 8) {
            isOwned = this.player.getPlayerData().getShopAdventureBanners().contains(itemId);
        }

        if (isOwned)
            return 2;

        return this.player.getParseInventory().getConsumableAmount(itemId) < itemCostQuantity ? 1 : 0;
    }
}