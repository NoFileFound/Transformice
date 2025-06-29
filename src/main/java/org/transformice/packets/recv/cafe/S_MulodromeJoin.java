package org.transformice.packets.recv.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.libraries.Pair;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.cafe.C_MulodromeJoin;
import org.transformice.packets.send.cafe.C_MulodromeLeave;

@SuppressWarnings("unused")
public final class S_MulodromeJoin implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int team = data.readByte();
        int position = data.readByte();

        if(client.mulodromeInfo != null) {
            client.getRoom().sendAll(new C_MulodromeLeave(client.mulodromeInfo.getFirst(), client.mulodromeInfo.getSecond()));
            client.getRoom().getBlueTeam().remove(client.getPlayerName());
            client.getRoom().getRedTeam().remove(client.getPlayerName());
        }

        client.mulodromeInfo = new Pair<>(team, position);
        client.getRoom().sendAll(new C_MulodromeJoin(team, position, client.getSessionId(), client.getPlayerName(), (client.isGuest()) ? "" : client.getAccount().getTribeName()));
        if (team == 1) {
            client.getRoom().getRedTeam().add(client.getPlayerName());
        } else {
            client.getRoom().getBlueTeam().add(client.getPlayerName());
        }
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}