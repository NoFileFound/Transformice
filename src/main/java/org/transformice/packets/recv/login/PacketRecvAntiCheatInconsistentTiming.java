package org.transformice.packets.recv.login;

// Imports
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.transformice.GameServer;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_ANTICHEAT_INCONSISTENT_TIMING)
public final class PacketRecvAntiCheatInconsistentTiming extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        gameServer.getLogger().warn(String.format("[AntiCheat][%s] (%s) has inconsistent timing with the game.", client.getIpAddress(), client.getPlayer().getPlayerName()));
        client.getPlayer().disconnect(true);
    }
}