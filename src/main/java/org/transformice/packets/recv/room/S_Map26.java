package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.send.lua.C_AddPhysicObject;

@SuppressWarnings("unused")
public final class S_Map26 implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(client.getRoom().getCurrentMap().mapCode == 26) {
            short posX = data.readShort();
            short posY = data.readShort();
            short width = data.readShort();
            short height = data.readShort();

            client.getRoom().sendAll(new C_AddPhysicObject(-1, false, 0, posX, posY, width, height, true, 0, 0, 0, true, true, true, 0, 0, 0, true, "", false));
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 8;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}