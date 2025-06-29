package org.transformice.packets.recv.informations;

// Imports
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.database.collections.Account;
import org.transformice.libraries.Pair;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_SendLetter;
import org.transformice.packets.send.informations.C_TranslationMessage;

@SuppressWarnings("unused")
public final class S_SendLetter implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        String playerName = data.readString();
        byte letterType = data.readByte();
        byte giftSize = data.readByte();

        List<Pair<Integer, Integer>> giftItems = new ArrayList<>(giftSize);
        for (int i = 0; i < giftSize; i++) {
            giftItems.add(new Pair<>(data.readInt(), data.readUnsignedShort()));
        }

        Account targetAccount = client.getServer().getPlayerAccount(playerName);
        if (targetAccount == null) {
            client.sendPacket(new C_TranslationMessage("", "$Joueur_Existe_Pas"));
            return;
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
            default -> throw new IllegalArgumentException("Unhandled letter type: " + letterType);
        };

        client.getParseInventoryInstance().removeConsumable(letterItemId, 1);

        ByteArray newContent = new ByteArray();
        newContent.writeUnsignedByte(giftSize);
        for (Pair<Integer, Integer> item : giftItems) {
            newContent.writeInt(item.getFirst()).writeUnsignedShort(item.getSecond());
        }
        newContent.writeBytes(data.readBytes(data.getLength()));

        byte[] contentBytes = newContent.toByteArray();
        String senderInfo = client.getPlayerName();
        String senderAppearance = client.getAccount().getMouseLook() + ";" + Integer.toHexString(client.getAccount().getMouseColor());
        giftItems.forEach(item -> client.getParseInventoryInstance().removeConsumable(item.getFirst(), item.getSecond()));
        if (client.getServer().checkIsConnected(playerName)) {
            Client recipient = client.getServer().getPlayers().get(playerName);
            recipient.sendPacket(new C_SendLetter(senderInfo, senderAppearance, letterType, contentBytes));
            giftItems.forEach(item -> recipient.getParseInventoryInstance().addConsumable(String.valueOf(item.getFirst()), item.getSecond(), false));
        } else {
            String encodedContent = Base64.getEncoder().encodeToString(contentBytes);
            String letter = String.join("|", senderInfo, senderAppearance, String.valueOf(letterType), encodedContent);
            targetAccount.getLetters().add(letter);
            for(var item : giftItems) {
                targetAccount.getInventory().putIfAbsent(String.valueOf(item.getFirst()), 0);
                targetAccount.getInventory().put(String.valueOf(item.getFirst()), targetAccount.getInventory().get(String.valueOf(item.getFirst())) + item.getSecond());
            }
            targetAccount.save();
        }

        client.sendPacket(new C_TranslationMessage("", "$MessageEnvoye"));
    }


    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}