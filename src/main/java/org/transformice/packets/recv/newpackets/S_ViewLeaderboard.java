package org.transformice.packets.recv.newpackets;

// Imports
import com.mongodb.client.MongoCollection;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.database.DBManager;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.SendPacket;

@SuppressWarnings("unused")
public final class S_ViewLeaderboard implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        ByteArray packet = new ByteArray();

        //packet.writeInt128(DBManager.getCounterValue("lastSeasonId"));
        packet.writeInt128(100); /// TODO: Implement periodically seasons.
        /// TODO: Finish the leaderboard

        String[] counters = {"cheeseCount", "firstCount", "shamanCheeseCount", "bootcampCount", "normalSaves", "hardSaves", "divineSaves", "shopCheeses"};
        MongoCollection<org.bson.Document> users = DBManager.getDataStore().getDatabase().getCollection("accounts");
        for (String counter : counters) {
            packet.writeInt128(0);
        }

        for(int i = 0; i < 8; i++) {
            packet.writeInt128(1);
            packet.writeInt128(1);
        }

        client.sendPacket(new SendPacket() {
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
                return packet.toByteArray();
            }
        });
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}