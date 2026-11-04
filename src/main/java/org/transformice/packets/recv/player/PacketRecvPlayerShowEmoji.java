package org.transformice.packets.recv.player;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerShowEmoji;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_PLAYER_SHOW_EMOJI)
public final class PacketRecvPlayerShowEmoji extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        var player = client.getPlayer();
        int emoji = psData.readUnsignedShort();
        if (emoji > 11 && !player.getPlayerData().getShopEmojis().contains(emoji))
            return;

        player.getPlayerRoom().broadcastPacket(player, new PacketSendPlayerShowEmoji(player.getSessionId(), emoji));
    }
}