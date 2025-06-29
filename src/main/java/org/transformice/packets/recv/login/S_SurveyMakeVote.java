package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.login.C_SurveyMakeVote;

@SuppressWarnings("unused")
public final class S_SurveyMakeVote implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int playerId = data.readInt();
        for (Client player : client.getServer().getPlayers().values()) {
            if (!player.isGuest() && player.getAccount().getId() == playerId) {
                player.sendPacket(new C_SurveyMakeVote(data.readByte()));
            }
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 17;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}