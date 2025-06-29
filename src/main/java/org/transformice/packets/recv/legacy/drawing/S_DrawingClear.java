package org.transformice.packets.recv.legacy.drawing;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.packets.SendPacket;

@SuppressWarnings("unused")
public final class S_DrawingClear implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.hasStaffPermission("Admin", "Drawing")) return;

        client.getRoom().sendAllOld(new SendPacket() {
            @Override
            public int getC() {
                return 25;
            }

            @Override
            public int getCC() {
                return 3;
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
        return 3;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}