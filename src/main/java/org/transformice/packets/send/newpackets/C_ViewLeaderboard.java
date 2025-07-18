package org.transformice.packets.send.newpackets;

// Imports
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.bytearray.ByteArray;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Account;
import org.transformice.libraries.Pair;
import org.transformice.packets.SendPacket;

public final class C_ViewLeaderboard implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ViewLeaderboard(String playerName) {
        Map<String, Pair<Integer, Integer>> myCounters = new HashMap<>();

        this.byteArray.writeInt128(1);
        this.byteArray.writeInt128(1000000);
        String[] counters = {"cheesecount", "firstcount", "shamancheesecount", "racing", "bootcampcount", "survivor", "defilante"};
        for (String counter : counters) {
            var players = DBUtils.findBest10PlayersByCriteria(counter);
            Map.Entry<Integer, Pair<Account, Integer>> matchEntry = IntStream.range(0, players.size())
                    .filter(i -> players.get(i).getFirst().getPlayerName().equals(playerName))
                    .mapToObj(i -> Map.entry(i, players.get(i)))
                    .findFirst()
                    .orElse(null);

            if (matchEntry != null) {
                myCounters.put(counter, new Pair<>(matchEntry.getKey()+1, matchEntry.getValue().getSecond()));
            }

            this.byteArray.writeInt128(players.size());
            for(int i = 0; i < players.size(); ++i) {
                this.byteArray.writeInt128((int)players.get(i).getFirst().getId()).writeString(players.get(i).getFirst().getPlayerName()).writeInt128(players.get(i).getSecond()).writeInt128(i+1);
            }
        }

        for (String counter : counters) {
            if(!myCounters.containsKey(counter)) {
                Pair<Integer, Integer> info = DBUtils.findLeaderboardPlayer(playerName, counter);
                if(info != null) {
                    this.byteArray.writeInt128(info.getFirst());
                    this.byteArray.writeInt128(info.getSecond());
                }
            } else {
                this.byteArray.writeInt128(myCounters.get(counter).getSecond());
                this.byteArray.writeInt128(myCounters.get(counter).getFirst());
            }
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