package org.transformice.packets.recv.tribe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;
import java.util.ArrayList;
import java.util.List;

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

        if(Application.getPropertiesInfo().event.event_name.isEmpty() || Application.getPropertiesInfo().event.banner_id != adventureId || client.isDead) {
            client.closeConnection();
            return;
        }

        if(Application.getPropertiesInfo().event.event_name.equals("Halloween") && client.getRoom().getCurrentMap().mapCode == 816) {
            int machineId = Integer.parseInt(actionIds.getFirst());
            if(client.getAccount().getInventory().get("2000") != null && client.getAccount().getInventory().get("2000") >= machineId) {
                client.getServer().lastHalloweenJackpointId += machineId;
                client.getParseInventoryInstance().removeConsumable(2000, machineId);

                int item1 = SrcRandom.RandomNumber(10, 20);
                int item2 = SrcRandom.RandomNumber(10, 20);
                int item3 = SrcRandom.RandomNumber(10, 20);
                if(item1 == item2 && item2 == item3) {
                    client.getParseInventoryInstance().addConsumable("2200", 1, true);
                } else if(item1 == item2 || item2 == item3 || item1 == item3) {
                    client.getParseInventoryInstance().addConsumable("2201", 1, true);
                }

                client.sendPacket(new C_AdventureAction(Application.getPropertiesInfo().event.banner_id, 1, List.of(String.valueOf(machineId), String.valueOf(item1), String.valueOf(item2), String.valueOf(item3))));
                client.getRoom().sendAllOthers(client, new C_AdventureAction(Application.getPropertiesInfo().event.banner_id, 2, List.of(String.valueOf(client.getServer().lastHalloweenJackpointId))));
            }
        }

        if(Application.getPropertiesInfo().event.event_name.equals("Halloween") && client.getRoom().getCurrentMap().mapCode == 926) {
            /// TODO: FINISH
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