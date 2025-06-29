package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.luaj.vm2.LuaTable;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_ContactListener implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if (client.getRoom().luaMinigame != null) {
            int contactedId = data.readInt128();
            int playerX = data.readInt128();
            int playerY = data.readInt128();
            int contactX = data.readInt128();
            int contactY = data.readInt128();
            int speedX = data.readInt128();
            int speedY = data.readInt128();

            LuaTable info = new LuaTable();
            info.set("speedX", speedX);
            info.set("speedY", speedY);
            info.set("playerX", playerX);
            info.set("playerY", playerY);
            info.set("contactX", contactX);
            info.set("contactY", contactY);

            client.getRoom().luaApi.callEvent("eventContactListener", client.getPlayerName(), contactedId, info);
        }
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 19;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}