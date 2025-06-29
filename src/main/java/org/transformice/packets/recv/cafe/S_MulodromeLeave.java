package org.transformice.packets.recv.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.cafe.C_MulodromeLeave;

@SuppressWarnings("unused")
public final class S_MulodromeLeave implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int team = data.readByte();
        int position = data.readByte();

        client.getRoom().sendAll(new C_MulodromeLeave(team, position));
        if (team == 1) {
            for (String playerName : client.getRoom().getRedTeam()) {
                if (client.getRoom().getPlayers().get(playerName).mulodromeInfo.getSecond() == position) {
                    client.getRoom().getRedTeam().remove(playerName);
                    break;
                }
            }

        } else {
            for (String playerName : client.getRoom().getBlueTeam()) {
                if (client.getRoom().getPlayers().get(playerName).mulodromeInfo.getSecond() == position) {
                    client.getRoom().getBlueTeam().remove(playerName);
                    break;
                }
            }
        }
    }

    @Override
    public int getC() {
        return 30;
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