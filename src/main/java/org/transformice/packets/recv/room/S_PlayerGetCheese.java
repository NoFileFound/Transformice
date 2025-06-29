package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_PlayerGetCheese implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int roundCode = data.readInt();
        short cheeseX = data.readShort();
        short cheeseY = data.readShort();
        int cheeseIndex = data.readByte();
        int contextId = data.readByte();
        short distance = data.readShort();
        if(roundCode == client.getRoom().getLastRoundId()) {
            client.sendGiveCheese(cheeseX, cheeseY, distance, cheeseIndex);
        }
    }

    @Override
    public int getC() {
        return 5;
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