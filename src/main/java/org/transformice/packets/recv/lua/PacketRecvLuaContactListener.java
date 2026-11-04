package org.transformice.packets.recv.lua;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_LUA_CONTACT_LISTENER)
public final class PacketRecvLuaContactListener extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        client.getPlayer().getPlayerRoom().getParseLua().handleEventContactListener(client.getPlayer(), psData.readInt128(), psData.readInt128(), psData.readInt128(), psData.readInt128(), psData.readInt128(), psData.readInt128(), psData.readInt128());
    }
}