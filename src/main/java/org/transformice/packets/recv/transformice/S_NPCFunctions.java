package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;
import org.transformice.packets.send.legacy.player.C_PlayerUnlockTitle;
import org.transformice.packets.send.login.C_OpenNPCShop;
import org.transformice.packets.send.player.C_PlayerUnlockBadge;

@SuppressWarnings("unused")
public final class S_NPCFunctions implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int functionId = data.readByte();
        switch (functionId) {
            case 4:
                // Open NPC Shop.
                String npcName = data.readString();
                client.lastNpcName = npcName;
                client.sendPacket(new C_OpenNPCShop(npcName, Application.getVillageNPCSInfo().get(npcName).items, client));
                break;
            case 10:
                /// Buy NPC Shop Item.
                int itemPosition = data.readByte();
                try {
                    var itemInfo = Application.getVillageNPCSInfo().get(client.lastNpcName).items.get(itemPosition);
                    if((client.getAccount().getInventory().get(String.valueOf(itemInfo.cost_id)) == null || client.getAccount().getInventory().get(String.valueOf(itemInfo.cost_id)) < itemInfo.cost_quantity)) {
                        return;
                    }

                    client.getParseInventoryInstance().removeConsumable(itemInfo.cost_id, itemInfo.cost_quantity);
                    switch(itemInfo.type) {
                        case 1:
                            client.getAccount().getShopBadges().putIfAbsent(itemInfo.item_id, 1);
                            client.getRoom().sendAll(new C_PlayerUnlockBadge(client.getSessionId(), itemInfo.item_id));
                            break;
                        case 2:
                            client.getAccount().getShamanBadges().add(itemInfo.item_id);
                            client.sendPacket(new C_TranslationMessage("", "$GainMacaron"));
                            break;
                        case 3:
                            client.getAccount().getTitleList().add(itemInfo.item_id + 0.1);
                            client.getRoom().sendAllOld(new C_PlayerUnlockTitle(client.getSessionId(), itemInfo.item_id, 1));
                            break;
                        case 4:
                            client.getParseInventoryInstance().addConsumable(String.valueOf(itemInfo.item_id), itemInfo.quantity, true);
                            break;
                        case 5:
                            client.getAccount().getShopItems().putIfAbsent(itemInfo.item_id, "");
                            break;
                        case 7:
                            client.getAccount().getPurchasedEmojis().add(itemInfo.item_id);
                            break;
                    }
                } catch (IndexOutOfBoundsException _) {
                    client.closeConnection();
                }
                client.sendPacket(new C_OpenNPCShop(client.lastNpcName, Application.getVillageNPCSInfo().get(client.lastNpcName).items, client));
                break;
            default:
                Application.getLogger().warn(Application.getTranslationManager().get("invalidnpcfunctioncode", functionId));
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 75;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}