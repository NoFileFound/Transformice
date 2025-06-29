package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_PlayerWin implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int holeType = data.readByte();
        int roundCode = data.readInt();
        int mapCode = data.readInt();
        short distance = data.readShort();
        short holeX = data.readShort();
        short holeY = data.readShort();
        if(roundCode == client.getRoom().getLastRoundId() && mapCode == client.getRoom().getCurrentMap().mapCode) {
            client.sendEnterHole(holeType, holeX, holeY, distance);
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 18;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}