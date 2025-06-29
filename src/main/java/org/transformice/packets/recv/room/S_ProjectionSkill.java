package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_ProjectionSkill;

@SuppressWarnings("unused")
public final class S_ProjectionSkill implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.isShaman && !client.isGuest() && client.getAccount().getPlayerSkills().containsKey(30) && !client.getAccount().isShamanNoSkills()) {
            client.getRoom().sendAllOthers(client, new C_ProjectionSkill(data.readShort(), data.readShort(), data.readShort()));
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 16;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}