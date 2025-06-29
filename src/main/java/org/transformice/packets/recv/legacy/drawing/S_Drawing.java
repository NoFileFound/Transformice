package org.transformice.packets.recv.legacy.drawing;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.SendPacket;

@SuppressWarnings("unused")
public final class S_Drawing implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.hasStaffPermission("Admin", "Drawing")) return;

        client.getRoom().sendAllOthersOld(client, new SendPacket() {
            @Override
            public int getC() {
                return 25;
            }

            @Override
            public int getCC() {
                return 5;
            }

            @Override
            public byte[] getPacket() {
                return data.toByteArray();
            }
        });
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}