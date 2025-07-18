package org.transformice.modules;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.libraries.SrcRandom;
import org.transformice.properties.configs.InventoryConfig;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;
import org.transformice.packets.send.inventory.*;
import org.transformice.packets.send.newpackets.C_SaveWallpaper;
import org.transformice.packets.send.player.C_GiveCurrency;
import org.transformice.packets.send.player.C_PlayerAction;
import org.transformice.packets.send.player.C_PlayerRaiseItem;
import org.transformice.packets.send.room.C_BonfireSkill;
import org.transformice.packets.send.transformice.C_NewConsumable;
import org.transformice.packets.send.transformice.C_LaunchHotAirBalloon;
import org.transformice.packets.send.transformice.C_SpawnPet;
import org.transformice.packets.send.transformice.C_VisualConsumableInfo;

public final class ParseInventory {
    private final Client client;
    private final Server server;

    /**
     * Creates a new instance of Inventory for the given player.
     * @param client The player.
     */
    public ParseInventory(final Client client) {
        this.client = client;
        this.server = client.getServer();
    }

    /**
     * Adds an item to current player.
     * @param itemId The item id.
     * @param quantity The item quantity.
     */
    public void addConsumable(String itemId, int quantity, boolean raise) {
        int consumableId = (itemId.contains("_")) ? Short.parseShort(itemId.substring(0, itemId.indexOf("_"))) : Short.parseShort(itemId);
        InventoryConfig.ConsumableInfo info = Application.getInventoryInfo().get(consumableId);
        if(quantity > info.limit) {
            quantity = info.limit;
        }

        this.getInventory().putIfAbsent(itemId, 0);
        int sum = this.getInventory().get(itemId) + quantity;

        this.getInventory().put(itemId, sum);
        this.client.sendPacket(new C_NewConsumable(consumableId, quantity));
        this.client.sendPacket(new C_UpdateInventoryPacket(consumableId, quantity));
        if(raise) {
            this.client.getRoom().sendAll(new C_PlayerRaiseItem(4, this.client.getSessionId(), new Object[]{consumableId}));
        }
    }

    /**
     * Adds/Removes an item in the tribe.
     * @param itemId The item id. (Consumable)
     * @param isAdd Is adding or is removing.
     */
    public void addTradeConsumable(int itemId, boolean isAdd) {
        Client player = this.server.getPlayers().get(this.client.currentTradeName);

        if(player != null && player.isOpenTrade && this.getInventory().containsKey(String.valueOf(itemId))) {
            InventoryConfig.ConsumableInfo info = Application.getInventoryInfo().get(itemId);
            if(!info.canTrade) return;

            if (isAdd) {
                if (this.client.getTradeConsumables().containsKey(itemId)) {
                    this.client.getTradeConsumables().replace(itemId, this.client.getTradeConsumables().get(itemId) + 1);
                } else {
                    this.client.getTradeConsumables().put(itemId, 1);
                }

            } else {
                int count = (this.client.getTradeConsumables().getOrDefault(itemId, 0)) - 1;
                if (count > 0) {
                    this.client.getTradeConsumables().replace(itemId, count);
                } else {
                    this.client.getTradeConsumables().remove(itemId);
                }
            }

            this.client.sendPacket(new C_TradeAddConsumable(true, (short)itemId, isAdd, 1, !info.images.isEmpty(), info.images.split(";")[0]));
            player.sendPacket(new C_TradeAddConsumable(false, (short)itemId, isAdd, 1, !info.images.isEmpty(), info.images.split(";")[0]));
        }
    }

    /**
     * Terminates the current trade with the given player.
     * @param playerName The player name.
     * @param isSuddenly When player leave the room/game.
     */
    public void closeTrade(String playerName, boolean isSuddenly) {
        Client player = this.server.getPlayers().get(playerName);
        if(player != null) {
            if(!isSuddenly) {
                this.client.currentTradeName = "";
                this.client.isOpenTrade = false;
                this.client.getTradeConsumables().clear();
                this.client.isTradeConfirm = false;
                player.currentTradeName = "";
                player.isOpenTrade = false;
                player.getTradeConsumables().clear();
                player.isTradeConfirm = false;
                player.sendPacket(new C_TradeResult(this.client.getPlayerName(), 2));
            }
        }
    }

    /**
     * Confirm or decline the given trade.
     * @param isAccept Is accepting.
     */
    public void confirmTrade(boolean isAccept) {
        Client player = this.server.getPlayers().get(this.client.currentTradeName);
        if(player != null && player.isOpenTrade) {
            this.client.isTradeConfirm = isAccept;
            player.sendPacket(new C_TradeLock(0, isAccept));
            this.client.sendPacket(new C_TradeLock(1, isAccept));
            if(this.client.isTradeConfirm && player.isTradeConfirm) {
                this.client.sendPacket(new C_TradeLock(2, false));

                for (var consumable : player.getTradeConsumables().entrySet()) {
                    this.addConsumable(String.valueOf(consumable.getKey()), consumable.getValue(), false);
                    player.getParseInventoryInstance().removeConsumable(consumable.getKey(), consumable.getValue());
                }

                for (var consumable : this.client.getTradeConsumables().entrySet()) {
                    player.getParseInventoryInstance().addConsumable(String.valueOf(consumable.getKey()), consumable.getValue(), false);
                    this.removeConsumable(consumable.getKey(), consumable.getValue());
                }

                this.client.currentTradeName = "";
                this.client.isOpenTrade = false;
                this.client.getTradeConsumables().clear();
                this.client.isTradeConfirm = false;
                this.client.sendPacket(new C_TradeResult("", 4));
                this.client.sendPacket(new C_TradeComplete());
                this.loadInventory();
                player.currentTradeName = "";
                player.isOpenTrade = false;
                player.getTradeConsumables().clear();
                player.isTradeConfirm = false;
                player.sendPacket(new C_TradeResult("", 4));
                player.sendPacket(new C_TradeComplete());
                player.getParseInventoryInstance().loadInventory();
            }
        }
    }

    /**
     * Loads the inventory.
     */
    public void loadInventory() {
        this.client.sendPacket(new C_LoadInventory(this.getInventory(), this.getEquippedInventory()));
    }

    /**
     * Loads the equipped items from the inventory.
     */
    public void loadEquippedInventory() {
        for(Integer item : this.getEquippedInventory()) {
            this.setEquipConsumable(item, true);
        }
    }

    /**
     * Equip the consumable by id.
     * @param consumableId The consumable id.
     * @param equip Equip.
     */
    public void setEquipConsumable(int consumableId, boolean equip) {
        List<Integer> consumableList = this.getEquippedInventory();

        if(equip) {
            if(consumableList.contains(consumableId)) {
                consumableList.remove(consumableList.indexOf(consumableId));
            }
            consumableList.add(consumableId);
        } else {
            consumableList.remove(consumableList.indexOf(consumableId));
        }
    }

    /**
     * Creates a new trade between you and the given player name.
     * @param playerName The player name.
     */
    public void startTrade(String playerName) {
        playerName = playerName.substring(0, 1).toUpperCase() + playerName.substring(1);
        Client player = this.server.getPlayers().get(playerName);
        if(player == null) {
            this.client.sendPacket(new C_TradeResult(playerName, 6));
            return;
        }

        if(player.isGuest() || this.client.isGuest()) {
            this.client.sendPacket(new C_TradeResult(playerName, 7));
            return;
        }

        if(!player.isOpenTrade) {
            if(!player.getRoom().getRoomName().equals(this.client.getRoom().getRoomName())) {
                this.client.sendPacket(new C_TradeResult(playerName, 3));
                return;
            }

            if(player.getIpAddress().equals(this.client.getIpAddress()) && !Application.getPropertiesInfo().is_debug) {
                return;
            }

            if(player.isShaman) {
                this.client.sendPacket(new C_TradeResult(playerName, 5));
            }

            this.client.sendPacket(new C_TranslationMessage("", "$Demande_Envoyée"));
            this.client.isOpenTrade = true;
            this.client.currentTradeName = playerName;
            player.sendPacket(new C_TradeInvite(this.client.getSessionId()));
        } else {
            if(!player.currentTradeName.equals(this.client.getPlayerName())) {
                this.client.sendPacket(new C_TradeResult(playerName, 0));
                return;
            }

            this.client.isOpenTrade = true;
            this.client.currentTradeName = playerName;
            this.client.sendPacket(new C_TradeStart(player.getSessionId()));
            player.sendPacket(new C_TradeStart(this.client.getSessionId()));
        }
    }

    /**
     * Removes an item from current player.
     * @param itemId The item id.
     * @param quantity The item quantity.
     */
    public void removeConsumable(int itemId, int quantity) {
        int sum = this.getInventory().get(String.valueOf(itemId)) - quantity;
        if(sum <= 0) {
            this.getInventory().remove(String.valueOf(itemId));
        } else {
            this.getInventory().put(String.valueOf(itemId), sum);
        }
        this.client.sendPacket(new C_UpdateInventoryPacket(itemId, Math.max(0, sum)));
    }

    /**
     * Handles the given consumable.
     */
    public void useConsumable(int consumableId) {
        if(!client.getParseInventoryInstance().getInventory().containsKey(String.valueOf(consumableId)) && consumableId != 4) return;

        boolean removeConsumable = true;
        switch (consumableId) {
            case 1:
            case 5:
            case 8:
            case 20:
            case 25:
            case 26:
                int objId = (consumableId == 1) ? 65 : (consumableId == 5) ? 6 : (consumableId == 8) ? 89 : (consumableId == 20) ? 33 : (consumableId == 25) ? 80 : 95;
                this.client.getRoom().sendPlaceObject(this.client.getRoom().getLastObjectID() + 1, objId, this.client.isFacingRight ? this.client.getPosition().getFirst() + 28 : this.client.getPosition().getFirst() - 28, this.client.isFacingRight ? this.client.getPosition().getSecond() - 20 : this.client.getPosition().getSecond() + 20, 0, this.client.isFacingRight ? 10 : -10, -3, true, false, new byte[]{}, null, true);
                break;
            case 10:
                for(Client player : this.client.getRoom().getPlayers().values()) {
                    if(player != this.client) {
                        if(player.getPosition().getFirst() >= this.client.getPosition().getFirst() - 400 && player.getPosition().getFirst() <= this.client.getPosition().getFirst() + 400) {
                            if(player.getPosition().getSecond() >= this.client.getPosition().getSecond() - 300 && player.getPosition().getSecond() <= this.client.getPosition().getSecond() + 300) {
                                this.client.getRoom().sendAll(new C_PlayerAction(player.getSessionId(), 3, "", false));
                            }
                        }
                    }
                }
                break;
            case 11:
                this.client.sendPlayerDeath();
                this.client.getRoom().sendPlaceObject(this.client.getRoom().getLastObjectID() + 1, 90, this.client.isFacingRight ? this.client.getPosition().getFirst() + 28 : this.client.getPosition().getFirst() - 28, this.client.isFacingRight ? this.client.getPosition().getSecond() - 20 : this.client.getPosition().getSecond() + 20, 0, this.client.isFacingRight ? 10 : -10, 0, true, false, new byte[]{}, null, true);
                break;
            case 21:
                this.client.getRoom().sendAll(new C_PlayerAction(this.client.getSessionId(), 12, "", false));
                break;
            case 24:
                this.client.getRoom().sendPlaceObject(this.client.getRoom().getLastObjectID() + 1, 63, this.client.isFacingRight ? this.client.getPosition().getFirst() + 28 : this.client.getPosition().getFirst() - 28, this.client.isFacingRight ? this.client.getPosition().getSecond() - 20 : this.client.getPosition().getSecond() + 20, 0, this.client.isFacingRight ? 10 : -10, 0, true, false, new byte[]{}, null, true);
                break;
            case 28:
                this.client.getRoom().sendAll(new C_BonfireSkill(this.client.getPosition().getFirst(), this.client.getPosition().getSecond(), 15));
                break;
            case 29:
            case 30:
            case 2241:
            case 2330:
            case 2351:
            case 2522:
            case 2576:
            case 2581:
            case 2591:
            case 2609:
            case 2612:
                removeConsumable = false;
                break;
            case 31:
            case 34:
            case 2240:
            case 2247:
            case 2262:
            case 2332:
            case 2340:
            case 2437:
            case 2444:
            case 2520:
            case 2532:
            case 2539:
            case 2545:
            case 2548:
            case 2551:
            case 2553:
            case 2554:
            case 2556:
            case 2575:
            case 2580:
            case 2586:
            case 2590:
            case 2602:
            case 2606:
            case 2618:
            case 2622:
            case 2625:
            case 2628:
            case 2633:
                if(this.client.getAccount().getPetType() == -1) {
                    int itemId = (consumableId == 31) ? 2 : (consumableId == 34) ? 3 : (consumableId == 2240) ? 4 : (consumableId == 2247) ? 5 : (consumableId == 2262) ? 6 : (consumableId == 2332) ? 7 : (consumableId == 2340) ? 8 : (consumableId == 2437) ? 9 : (consumableId == 2444) ? 10 : (consumableId == 2520) ? 12 : (consumableId == 2532) ? 13 : (consumableId == 2539) ? 14 : (consumableId == 2545) ? 15 : (consumableId == 2548) ? 16 : (consumableId == 2551) ? 17 : (consumableId == 2553) ? 18 : (consumableId == 2554) ? 19 : (consumableId == 2556) ? 20 : (consumableId == 2575) ? 21 : (consumableId == 2580) ? 22 : (consumableId == 2586) ? 23 : (consumableId == 2590) ? 24 : (consumableId == 2602) ? 25 : (consumableId == 2606) ? 26 : (consumableId == 2618) ? 27 : (consumableId == 2622) ? 28 : (consumableId == 2625) ? 29 : (consumableId == 2628) ? 30 : 31;
                    this.client.getAccount().setPetType(itemId);
                    this.client.getAccount().setLastPetTime(getUnixTime() + 3600);
                    this.client.getRoom().sendAll(new C_SpawnPet(this.client.getSessionId(), itemId));
                } else {
                    removeConsumable = false;
                }
                break;
            case 32:
                this.client.getRoom().sendAll(new C_PlayerRaiseItem(4, this.client.getSessionId(), new Object[]{this.getAstrologicalCheeseId(this.client.getAccount().getRegDate())}));
                break;
            case 33:
                this.client.getRoom().sendAll(new C_PlayerAction(this.client.getSessionId(), 16, "", false));
                break;
            case 35:
                removeConsumable = (!this.client.getAccount().getShopBadges().isEmpty());
                if(removeConsumable) {
                    this.client.getRoom().sendAll(new C_LaunchHotAirBalloon(this.client.getSessionId(),  this.client.getAccount().getShopBadges().get(SrcRandom.RandomNumber(0, this.client.getAccount().getShopBadges().size() - 1))));
                }
                break;
            case 800:
                this.client.getRoom().sendAll(new C_PlayerRaiseItem(2, this.client.getSessionId(), new Object[]{0}));
                this.client.sendPacket(new C_GiveCurrency(0, 1));
                this.client.getAccount().setShopCheeses(this.client.getAccount().getShopCheeses() + 1);
                break;
            case 801:
                this.client.getRoom().sendAll(new C_PlayerRaiseItem(2, this.client.getSessionId(), new Object[]{2}));
                this.client.sendPacket(new C_GiveCurrency(1, 1));
                this.client.getAccount().setShopStrawberries(this.client.getAccount().getShopStrawberries() + 1);
                break;
            case 2234:
                this.client.getRoom().sendAll(new C_PlayerAction(this.client.getSessionId(), 20, "", false));
                for(Client player : this.client.getRoom().getPlayers().values()) {
                    if(player != this.client) {
                        if(player.getPosition().getFirst() >= this.client.getPosition().getFirst() - 400 && player.getPosition().getFirst() <= this.client.getPosition().getFirst() + 400) {
                            if(player.getPosition().getSecond() >= this.client.getPosition().getSecond() - 300 && player.getPosition().getSecond() <= this.client.getPosition().getSecond() + 300) {
                                this.client.getRoom().sendAll(new C_PlayerAction(player.getSessionId(), 6, "", false));
                            }
                        }
                    }
                }
                break;
            case 2239:
                this.client.getRoom().sendAll(new C_VisualConsumableInfo(4, this.client.getSessionId(), new Object[]{this.client.getAccount().getShopCheeses()}));
                break;
            case 2246:
                this.client.getRoom().sendAll(new C_PlayerAction(this.client.getSessionId(), 24, "", false));
                break;
            case 2252:
            case 2256:
            case 2349:
            case 2379:
            case 2513:
            case 2514:
            case 2634:
            case 2635:
                this.client.drawColor = (consumableId == 2252 ? 5687614 : consumableId == 2256 ? 13188682 : consumableId == 2349 ? 4308730 : consumableId == 2379 ? 16745472 : consumableId == 2513 ? 11354851 : consumableId == 2514 ? 15910958 : consumableId == 2634 ? 16777215 : 0);
                this.client.getRoom().sendAll(new C_VisualConsumableInfo(1, this.client.getSessionId(), new Object[]{500, this.client.drawColor}));
                break;
            case 2250:
                this.client.getRoom().sendPlaceObject(this.client.getRoom().getLastObjectID() + 1, 97, this.client.isFacingRight ? this.client.getPosition().getFirst() + 28 : this.client.getPosition().getFirst() - 28, this.client.isFacingRight ? this.client.getPosition().getSecond() - 20 : this.client.getPosition().getSecond() + 20, 0, this.client.isFacingRight ? 10 : -10, -3, true, false, new byte[]{}, null, true);
                break;
            case 2255:
                this.client.getRoom().sendAll(new C_PlayerRaiseItem(7, this.client.getSessionId(), new Object[]{"$De6", SrcRandom.RandomNumber(1, 6)}));
                break;
            case 2259:
                long time = this.client.getAccount().getPlayedTime();
                this.client.getRoom().sendAll(new C_VisualConsumableInfo(5, this.client.getSessionId(), new Object[]{(int)(time / 86400), (int)((time / 3600) % 24)}));
                break;
            case 2578:
                this.client.getRoom().sendPlaceObject(this.client.getRoom().getLastObjectID() + 1, 7, this.client.isFacingRight ? this.client.getPosition().getFirst() + 28 : this.client.getPosition().getFirst() - 28, this.client.isFacingRight ? this.client.getPosition().getSecond() - 20 : this.client.getPosition().getSecond() + 20, 0, this.client.isFacingRight ? 10 : -10, 0, true, false, new byte[]{}, null, true);
                break;
            case 2579:
                this.client.getRoom().sendPlaceObject(this.client.getRoom().getLastObjectID() + 1, 2, this.client.isFacingRight ? this.client.getPosition().getFirst() + 28 : this.client.getPosition().getFirst() - 28, this.client.isFacingRight ? this.client.getPosition().getSecond() - 20 : this.client.getPosition().getSecond() + 20, 0, this.client.isFacingRight ? 10 : -10, 0, false, false, new byte[]{}, null, true);
                break;
            case 2582:
            case 2616:
            case 2619:
            case 2623:
                String fileName = (consumableId == 2616) ? "elisah" : (consumableId == 2619) ? "potager" : (consumableId == 2623) ? "" : "fishing";
                this.client.sendPacket(new C_SaveWallpaper(fileName));
                break;
        }

        if(removeConsumable) {
            this.removeConsumable(consumableId, 1);
            this.client.getRoom().sendAll(new C_UseConsumable(this.client.getSessionId(), (short)consumableId));
        } else {
            this.client.sendPacket(new C_UseConsumable(this.client.getSessionId(), (short)consumableId));
        }
    }

    /**
     * Gets the cheese id (astrological cheese) based on timestamp.
     * @param timestamp The given timestamp.
     * @return The cheese id. (Range between 2212 and 2224).
     */
    @SuppressWarnings("ConstantConditions")
    private int getAstrologicalCheeseId(long timestamp) {
        LocalDate date = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) {
            return 2213;
        } else if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
            return 2214;
        } else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
            return 2215;
        } else if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
            return 2216;
        } else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
            return 2217;
        } else if ((month == 5 && day >= 21) || (month == 6 && day <= 21)) {
            return 2218;
        } else if ((month == 6 && day >= 22) || (month == 7 && day <= 22)) {
            return 2219;
        } else if ((month == 7 && day >= 23) || (month == 8 && day <= 23)) {
            return 2220;
        } else if ((month == 8 && day >= 24) || (month == 9 && day <= 22)) {
            return 2221;
        } else if ((month == 9 && day >= 23) || (month == 10 && day <= 23)) {
            return 2212;
        } else if ((month == 10 && day >= 24) || (month == 11 && day <= 22)) {
            return 2222;
        }

        return 2223;
    }

    /**
     * Gets the equipped consumables of the current player.
     * @return The equipped consumables.
     */
    private List<Integer> getEquippedInventory() {
        return this.client.getAccount().getEquippedConsumables();
    }

    /**
     * Gets the inventory of the current player.
     * @return The inventory.
     */
    private Map<String, Integer> getInventory() {
        return this.client.getAccount().getInventory();
    }
}