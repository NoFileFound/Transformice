package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.C_PlayerTransformIceCube;

@SuppressWarnings("unused")
public final class S_TransformIceCube implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int actionType = data.readByte();
        int seconds = data.readShort();

        client.getRoom().sendAll(new C_PlayerTransformIceCube(client.getSessionId(), actionType, seconds));
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 45;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}