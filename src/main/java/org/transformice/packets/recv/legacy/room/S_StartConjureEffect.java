package org.transformice.packets.recv.legacy.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.SendPacket;

@SuppressWarnings("unused")
public final class S_StartConjureEffect implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.getRoom().getCurrentMap().isConj) return;

        client.getRoom().sendAllOld(new SendPacket() {
            @Override
            public int getC() {
                return 4;
            }

            @Override
            public int getCC() {
                return 12;
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
        return 12;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}