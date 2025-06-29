package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_ConvertSkill;

@SuppressWarnings("unused")
public final class S_ConvertSkill implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isShaman && !client.isGuest() && client.getAccount().getPlayerSkills().containsKey(52) && !client.getAccount().isShamanNoSkills()) {
            client.getRoom().sendAll(new C_ConvertSkill(data.readInt(), false));
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}