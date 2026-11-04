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
import org.transformice.packets.send.language.PacketSendLanguageList;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_LANGUAGE_LIST)
public final class PacketRecvLanguageList extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        client.sendData(new PacketSendLanguageList(client.getCountryLanguageCode(), client.getCountryCode()));
    }
}