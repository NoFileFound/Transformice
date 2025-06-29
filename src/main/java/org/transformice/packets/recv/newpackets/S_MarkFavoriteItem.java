package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_MarkFavoriteItem implements RecvPacket {
    @Override
    @SuppressWarnings("all")
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int itemId = data.readInt();
        boolean isFavorite = data.readBoolean();
        if(isFavorite) {
            if(client.getAccount().getFavoritedItems().contains(itemId))
                client.getAccount().getFavoritedItems().remove(client.getAccount().getFavoritedItems().indexOf(itemId));

            client.getAccount().getFavoritedItems().add(itemId);
        } else {
            client.getAccount().getFavoritedItems().remove(client.getAccount().getFavoritedItems().indexOf(itemId));
        }
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 27;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}