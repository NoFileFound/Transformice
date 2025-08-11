package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.libraries.Pair;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_MapInfo implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int cheeseLen = data.readByte() / 2;
        for (int i = 0; i < cheeseLen; i++) {
            if(i > 30) continue;

            short cheeseX = data.readShort();
            short cheeseY = data.readShort();
            client.getRoom().getCheesesList().add(new Pair<>((int)cheeseX, (int)cheeseY));
        }

        int holeLen = data.readByte() / 3;
        for(int i = 0; i < holeLen; i++) {
            if(i > 30) continue;
            short holeType = data.readShort();
            short holeX = data.readShort();
            short holeY = data.readShort();
            client.getRoom().getHolesList().add(new Pair<>((int)holeType, new Pair<>((int)holeX, (int)holeY)));
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 80;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}