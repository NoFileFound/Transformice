package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.data.objects.SkillType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_BUY_SKILL)
public final class PacketRecvPlayerBuySkill extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        var player = client.getPlayer();
        var data = player.getPlayerData();
        var skills = data.getShamanSkills();
        SkillType type = SkillType.fromValue(psData.readByte());
        if (type == null)
            return;

        int points = skills.getOrDefault(type, 0);
        if (points >= 5)
            return;

        int skillCount = 0;
        for (int value : skills.values())
            skillCount += value;

        if (skillCount >= data.shamanLevel)
            return;

        skills.merge(type, 1, Integer::sum);
        player.getParseSkills().sendShamanSkills(true);
    }
}