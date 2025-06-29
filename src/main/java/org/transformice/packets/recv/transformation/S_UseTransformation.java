package org.transformice.packets.recv.transformation;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.Room;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.transformation.C_Transformation;

@SuppressWarnings("unused")
public final class S_UseTransformation implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        Room room = client.getRoom();
        if(room.getRoomFunCorpPlayersTransformationAbility().contains(client.getPlayerName()) || room.getCurrentMap().isTransform || client.canTransform) {
            room.sendAll(new C_Transformation(client.getSessionId(), data.readShort()));
        }
    }

    @Override
    public int getC() {
        return 27;
    }

    @Override
    public int getCC() {
        return 11;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}