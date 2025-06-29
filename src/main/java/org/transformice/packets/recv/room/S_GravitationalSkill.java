package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_GravitationalSkill;

@SuppressWarnings("unused")
public final class S_GravitationalSkill implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int velX = data.readShort();
        int velY = data.readShort();

        client.getRoom().sendAll(new C_GravitationalSkill(velX, velY, 0));
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 28;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}