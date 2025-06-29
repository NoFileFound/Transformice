package org.transformice.packets.recv.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.transformice.C_VisualConsumableInfo;

@SuppressWarnings("unused")
public final class S_VisualConsumableInfo implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int code = data.readUnsignedByte();
        switch (code) {
            case 2:
                client.getRoom().sendAllOthers(client, new C_VisualConsumableInfo(2, client.getSessionId(), new Object[]{client.drawColor, data.readInt(), data.readInt(), data.readInt(), data.readInt()}));
                break;
            case 3:
                client.getRoom().sendAll(new C_VisualConsumableInfo(3, client.getSessionId(), new Object[]{client.getPlayerName()}));
                break;
        }
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 40;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}