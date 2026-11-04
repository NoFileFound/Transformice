package org.transformice.packets.recv.language;

// Imports
import java.util.Map;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.language.PacketSendOpenLink;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_OPEN_GAME_PARTNER)
public final class PacketRecvOpenGamePartner extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        String partnerName = psData.readUTF();
        for (Map<String, String> partner : gameServer.getConfig().getGameConfig().getPartners()) {
            if (partner.get("name").equals(partnerName)) {
                client.getPlayer().broadcastPacket(new PacketSendOpenLink(partner.get("url")));
            }
        }
    }
}