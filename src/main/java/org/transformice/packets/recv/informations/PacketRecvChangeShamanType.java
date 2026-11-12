package org.transformice.packets.recv.informations;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.data.objects.ShamanType;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CHANGE_SHAMAN_TYPE)
public final class PacketRecvChangeShamanType extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        var playerData = client.getPlayer().getPlayerData();
        int shamanType = psData.readByte();
        if (shamanType == 1 && playerData.savesNormalWithSkill < 1000)
            return;

        if (shamanType == 2 && (playerData.savesNormalWithSkill < 5000 || playerData.savesHardWithSkill < 2000))
            return;

        playerData.shamanType = (shamanType == 0 ? ShamanType.NORMAL : shamanType == 1 ? ShamanType.HARD : ShamanType.DIVINE);
        playerData.shamanSkillsEnabled = !psData.readBoolean();
        client.getPlayer().getParseSkills().sendShamanType();
    }
}