package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_EnterAdventureArea implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int adventureId = data.readUnsignedByte();
        int areaId = data.readShort();

        if(Application.getPropertiesInfo().event.event_name.isEmpty() || Application.getPropertiesInfo().event.banner_id != adventureId) {
            client.closeConnection();
            return;
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 44;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}