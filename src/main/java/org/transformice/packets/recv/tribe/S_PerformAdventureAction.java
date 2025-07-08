package org.transformice.packets.recv.tribe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import java.util.ArrayList;

// Packets
import org.transformice.packets.send.tribe.C_AdventureAction;

@SuppressWarnings("unused")
public final class S_PerformAdventureAction implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int adventureId = data.readUnsignedByte();
        int actionType = data.readUnsignedByte();
        int actionIdsSize = data.readUnsignedByte();
        ArrayList<String> actionIds = new ArrayList<>(actionIdsSize);
        for (int i = 0; i < actionIdsSize; i++) {
            actionIds.add(data.readString());
        }

        if(Application.getPropertiesInfo().event.event_name.isEmpty() || Application.getPropertiesInfo().event.banner_id != adventureId) {
            client.closeConnection();
            return;
        }

        client.sendPacket(new C_AdventureAction(adventureId, actionType, actionIds));
    }

    @Override
    public int getC() {
        return 16;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}