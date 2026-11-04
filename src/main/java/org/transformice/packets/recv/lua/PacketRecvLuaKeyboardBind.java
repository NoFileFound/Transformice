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
@PSPacket(id = PacketOpcodes.RECV_LUA_BIND_KEYBOARD)
public final class PacketRecvLuaKeyboardBind extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        psData.decryptPacket(gameServer.getConfig().getSWFConfig().getPacketKeys(), psHeader.readByte());
        client.getPlayer().getPlayerRoom().getParseLua().handleKeyboard(client.getPlayer(), psData.readInt128(), psData.readBoolean(), psData.readInt128(), psData.readInt128(), psData.readInt128() * 10, psData.readInt128() * 10);
    }
}