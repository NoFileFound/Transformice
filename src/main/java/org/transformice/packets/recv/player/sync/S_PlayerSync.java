package org.transformice.packets.recv.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.SendPacket;

@SuppressWarnings("unused")
public final class S_PlayerSync implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int roundCode = data.readInt();
        if(roundCode == client.getRoom().getLastRoundId()) {
            ByteArray packet2 = new ByteArray();
            while (data.getLength() > 0) {
                packet2.writeInt(data.readInt());

                short code = data.readShort();
                packet2.writeShort(code);
                if(code != -1) {
                    packet2.writeInt(data.readInt());
                    packet2.writeInt(data.readInt());
                    packet2.writeShort(data.readShort());
                    packet2.writeShort(data.readShort());
                    packet2.writeShort(data.readShort());
                    packet2.writeShort(data.readShort());
                    packet2.writeBoolean(data.readBoolean());
                    packet2.writeBoolean(data.readBoolean());
                    packet2.writeBoolean(true);
                }
            }

            if ((((!client.getRoom().isChanged20secTimer() ? client.getRoom().getRoundTime() + client.getRoom().addTime : 20) * 1000L) + (client.getRoom().getGameStartTimeMillis() - System.currentTimeMillis())) > 5000 && (client.getRoom().getGameStartTimeMillis() - System.currentTimeMillis()) < -5000) {
                client.getRoom().sendAllOthers(client, new SendPacket() {
                    @Override
                    public int getC() {
                        return 4;
                    }

                    @Override
                    public int getCC() {
                        return 3;
                    }

                    @Override
                    public byte[] getPacket() {
                        return packet2.toByteArray();
                    }
                });
            }
        }
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}