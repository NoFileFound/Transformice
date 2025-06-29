package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_PlayerBuySkill implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int skill = data.readByte();
        if (client.getAccount().getShamanLevel() - 1 > client.getAccount().getPlayerSkills().size()) {
            if (client.getAccount().getPlayerSkills().containsKey(skill)) {
                client.getAccount().getPlayerSkills().replace(skill, client.getAccount().getPlayerSkills().get(skill) + 1);
            } else {
                client.getAccount().getPlayerSkills().put(skill, 1);
            }

            client.getParseSkillsInstance().sendShamanSkills(true);
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}