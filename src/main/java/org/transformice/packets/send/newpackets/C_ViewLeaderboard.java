package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.database.DBUtils;
import org.transformice.packets.SendPacket;

public final class C_ViewLeaderboard implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ViewLeaderboard(String playerName) {
        this.byteArray.writeInt128(1);
        this.byteArray.writeInt128(1000000);
        String[] counters = {"cheesecount", "firstcount", "shamancheesecount", "racing", "bootcampcount", "survivor", "defilante"};
        for (String counter : counters) {
            var players = DBUtils.findBest10PlayersByCriteria(counter);
            this.byteArray.writeInt128(players.size());
            for(int i = 0; i < players.size(); ++i) {
                this.byteArray.writeInt128((int)players.get(i).getFirst().getId()).writeString(players.get(i).getFirst().getPlayerName()).writeInt128(players.get(i).getSecond()).writeInt128(i);
            }
        }

        for (String counter : counters) {
            this.byteArray.writeInt128(1);
            this.byteArray.writeInt128(1);
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 36;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}