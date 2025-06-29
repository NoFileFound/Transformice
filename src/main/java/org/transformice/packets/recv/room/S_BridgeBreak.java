package org.transformice.packets.recv.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.room.C_BridgeBreak;

@SuppressWarnings("unused")
public final class S_BridgeBreak implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        short bridgeCode = data.readShort();
        client.getRoom().sendAllOthers(client, new C_BridgeBreak(bridgeCode));
        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventBridgeBreak", bridgeCode);
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 24;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}