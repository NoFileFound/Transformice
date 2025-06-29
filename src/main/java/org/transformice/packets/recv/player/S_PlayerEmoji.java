package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.C_PlayerEmoji;

@SuppressWarnings("unused")
public final class S_PlayerEmoji implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int emoji = data.readUnsignedShort();
        if(emoji > 11 && (!client.getAccount().getPurchasedEmojis().contains(emoji))) return;

        client.getRoom().sendAllOthers(client, new C_PlayerEmoji(client.getSessionId(), emoji));
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}