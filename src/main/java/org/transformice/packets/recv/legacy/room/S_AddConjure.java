package org.transformice.packets.recv.legacy.room;

// Imports
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.libraries.Timer;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.SendPacket;

@SuppressWarnings("unused")
public final class S_AddConjure implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.getRoom().getCurrentMap().isConj) return;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                client.getRoom().sendAllOld(new SendPacket() {
                    @Override
                    public int getC() {
                        return 4;
                    }

                    @Override
                    public int getCC() {
                        return 15;
                    }

                    @Override
                    public byte[] getPacket() {
                        return data.toByteArray();
                    }
                });
            }
        }, 4, TimeUnit.SECONDS);

        client.getRoom().sendAllOld(new SendPacket() {
            @Override
            public int getC() {
                return 4;
            }

            @Override
            public int getCC() {
                return 14;
            }

            @Override
            public byte[] getPacket() {
                return data.toByteArray();
            }
        });
    }

    @Override
    public int getC() {
        return 4;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}