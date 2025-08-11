package org.transformice.packets.recv.lua;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;
import org.transformice.packets.send.player.C_MovePlayer;

@SuppressWarnings("unused")
public final class S_MouseClick implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int posX = data.readInt128();
        int posY = data.readInt128();

        if(client.isDebugTeleport) {
            client.sendPacket(new C_MovePlayer(posX, posY, false, 0, 0, false));
        }

        if(client.isDebugCoords) {
            client.sendPacket(new C_ServerMessage(true, String.format("PosX: %d, PosY: %d", posX, posY)));
        }

        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventMouse", client.getPlayerName(), posX, posY);
        }
    }

    @Override
    public int getC() {
        return 29;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}