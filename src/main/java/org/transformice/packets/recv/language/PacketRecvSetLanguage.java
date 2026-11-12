package org.transformice.packets.recv.language;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.language.PacketSendSetLanguage;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_SET_LANGUAGE)
public final class PacketRecvSetLanguage extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        String languageId = psData.readUTF();
        client.setCurrentCommunity(languageId);
        client.sendData(new PacketSendSetLanguage(languageId, client.getCountryCode(), client.getCountryLanguageCode()));
    }
}