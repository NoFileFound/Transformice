package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_HandymouseSkill;
import org.transformice.packets.send.room.C_Skill;

@SuppressWarnings("unused")
public final class S_HandymouseSkill implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int objectID = data.readInt();
        int handyMouseByte = data.readByte();
        if(client.isShaman && !client.isGuest() && client.getAccount().getPlayerSkills().containsKey(85) && !client.getAccount().isShamanNoSkills()) {
            if (client.getRoom().lastHandymouse[0] == -1) {
                client.getRoom().lastHandymouse = new int[] {objectID, handyMouseByte};
            } else {
                client.getRoom().sendAll(new C_HandymouseSkill(handyMouseByte, objectID, client.getRoom().lastHandymouse[1], client.getRoom().lastHandymouse[0]));
                client.getRoom().sendAll(new C_Skill(77, 1));
                client.getRoom().lastHandymouse = new int[] {-1, -1};
            }
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 35;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}