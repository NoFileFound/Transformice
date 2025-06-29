package org.transformice.packets.recv.legacy.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import java.nio.charset.StandardCharsets;

// Packets
import org.transformice.packets.send.legacy.room.C_AddAnchor;

@SuppressWarnings("unused")
public final class S_AddAnchor implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        String anchor = new String(data.toByteArray(), StandardCharsets.UTF_8);
        client.getRoom().sendAllOld(new C_AddAnchor(anchor));

        if(!client.getRoom().getRoomAnchors().contains(anchor)) {
            client.getRoom().getRoomAnchors().add(anchor);
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}