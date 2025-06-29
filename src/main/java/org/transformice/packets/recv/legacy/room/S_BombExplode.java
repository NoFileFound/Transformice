package org.transformice.packets.recv.legacy.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.SendPacket;

@SuppressWarnings("unused")
public final class S_BombExplode implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int currentMap = client.getRoom().getCurrentMap().mapCode;
        if(currentMap == 117 || currentMap == 118 || currentMap == 120 || currentMap == 14 || currentMap == 28 || currentMap == 29 || currentMap == 33 || currentMap == 7 || currentMap == 8) {
            client.getRoom().sendAllOld(new SendPacket() {
                @Override
                public int getC() {
                    return 4;
                }

                @Override
                public int getCC() {
                    return 6;
                }

                @Override
                public byte[] getPacket() {
                    return data.toByteArray();
                }
            });
        }
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}