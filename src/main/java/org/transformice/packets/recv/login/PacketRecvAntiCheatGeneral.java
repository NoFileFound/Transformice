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
@PSPacket(id = PacketOpcodes.RECV_ANTICHEAT_GENERAL)
public final class PacketRecvAntiCheatGeneral extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        int cheatCode = psData.readShort();
        switch (cheatCode) {
            case 60:
                gameServer.getLogger().warn(String.format("[AntiCheat][%s] (%s) has tried to join the game with hacked client (Pgiex).", client.getIpAddress(), client.getPlayer().getPlayerName()));
                break;
            case 62:
            case 64:
                gameServer.getLogger().warn(String.format("[AntiCheat][%s] (%s) has tried to join the game with modified swf.", client.getIpAddress(), client.getPlayer().getPlayerName()));
                break;
            case 65:
                gameServer.getLogger().warn(String.format("[AntiCheat][%s] (%s) has tried to use a macro.", client.getIpAddress(), client.getPlayer().getPlayerName()));
                break;
        }

        gameServer.sendPlayerBan(client.getPlayer().getPlayerName(), 24, "Hack", null, true);
    }
}