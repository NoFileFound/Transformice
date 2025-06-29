package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_RestorativeSkill;

@SuppressWarnings("unused")
public final class S_RestorativeSkill implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isShaman && !client.isGuest() && client.getAccount().getPlayerSkills().containsKey(60) && !client.getAccount().isShamanNoSkills()) {
            client.getRoom().sendAll(new C_RestorativeSkill(data.readInt(), data.readInt()));
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 26;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}