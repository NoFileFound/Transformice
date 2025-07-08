package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_RecvQuestion implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int questionType = data.readByte();
        if(questionType == 1) {
            if (client.getRoom().luaMinigame != null) {
                client.getRoom().luaApi.callEvent("eventQuestionAnswered", client.getPlayerName(), data.readString(), data.readInt(), data.readBoolean(), data.readBoolean(), data.readBoolean());
            }
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 50;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}