package org.transformice.packets.recv.informations;

// Imports
import java.util.Arrays;
import java.util.Map;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSBase64;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.config.inventory.ConsumableConfig;
import org.transformice.database.data.PlayerEntity;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.informations.PacketSendPlayerSendLetter;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_SEND_LETTER)
public final class PacketRecvPlayerSendLetter extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        String playerName = psData.readUTF();
        byte letterType = psData.readByte();
        int giftSize = psData.readUnsignedByte();
        int[] itemIds = new int[giftSize];
        int[] quantities = new int[giftSize];
        for (int i = 0; i < giftSize; i++) {
            itemIds[i] = psData.readInt();
            quantities[i] = psData.readUnsignedShort();
        }

        int letterItemId = switch (letterType) {
            case 0 -> 29;
            case 1 -> 30;
            case 2 -> 2241;
            case 3 -> 2330;
            case 4 -> 2351;
            case 5 -> 2522;
            case 6 -> 2576;
            case 7 -> 2581;
            case 8 -> 2585;
            case 9 -> 2591;
            case 10 -> 2609;
            case 11 -> 2612;
            default -> -1;
        };

        if (letterItemId == -1)
            return;

        PlayerEntity targetAccount = gameServer.getDatabase().loadPlayerByName(playerName);
        if (targetAccount == null) {
            client.getPlayer().broadcastPacket(new PacketSendTranslatedMessage("", "$Joueur_Existe_Pas"));
            return;
        }

        Map<Integer, PlayerEntity.InventoryItem> senderItems = client.getPlayer().getPlayerData().getInventory();
        PlayerEntity.InventoryItem letterItem = senderItems.get(letterItemId);
        if (letterItem == null || letterItem.itemAmount < 1)
            return;

        for (int i = 0; i < giftSize; i++) {
            if (quantities[i] <= 0)
                return;

            PlayerEntity.InventoryItem item = senderItems.get(itemIds[i]);
            if (item == null || item.itemAmount < quantities[i])
                return;
        }

        for (int i = 0; i < giftSize; i++) {
            for (int j = i + 1; j < giftSize; j++) {
                if (itemIds[i] == itemIds[j]) {
                    return;
                }
            }
        }

        NetByteArray newContent = new NetByteArray();
        newContent.writeUnsignedByte(giftSize);
        for (int i = 0; i < giftSize; i++)
            newContent.writeInt(itemIds[i]).writeUnsignedShort(quantities[i]);

        newContent.writeBytes(psData.readBytes(psData.getLength()));
        byte[] contentBytes = newContent.toByteArray();
        String senderInfo = client.getPlayer().getPlayerName();
        String senderAppearance = client.getPlayer().getPlayerData().playerLook + ";" + Integer.toHexString(client.getPlayer().getPlayerData().mouseColor);
        client.getPlayer().getParseInventory().removeConsumable(letterItemId, 1);
        for (int i = 0; i < giftSize; i++)
            client.getPlayer().getParseInventory().removeConsumable(itemIds[i], quantities[i]);

        GamePlayer recipient = gameServer.getPlayers().get(playerName);
        if (recipient != null) {
            recipient.broadcastPacket(new PacketSendPlayerSendLetter(senderInfo, senderAppearance, letterType, contentBytes));
            for (int i = 0; i < giftSize; i++) {
                recipient.getParseInventory().addConsumable(String.valueOf(itemIds[i]), quantities[i], false);
            }
        } else {
            String letter = String.join("|", senderInfo, senderAppearance, String.valueOf(letterType), Arrays.toString(PSBase64.encode(contentBytes)));
            targetAccount.getLetters().add(letter);
            for (int i = 0; i < giftSize; i++) {
                PlayerEntity.InventoryItem item = targetAccount.getInventory().get(itemIds[i]);
                if (item == null) {
                    ConsumableConfig config = gameServer.getInventoryConfig().get(itemIds[i]);
                    if (config == null)
                        continue;

                    targetAccount.getInventory().put(itemIds[i], new PlayerEntity.InventoryItem(itemIds[i], quantities[i], config.getImage()));
                } else {
                    item.itemAmount += quantities[i];
                }
            }

            gameServer.getDatabase().savePlayer(targetAccount);
        }

        client.getPlayer().broadcastPacket(new PacketSendTranslatedMessage("", "$MessageEnvoye"));
    }
}