package org.transformice.packets.send.newpackets;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RoomPlayerList implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RoomPlayerList(List<ByteArray> playerData) {
        this.byteArray.writeUnsignedShort(playerData.size());
        for(var playerInfo : playerData) {
            this.byteArray.writeBytes(playerInfo.toByteArray());
        }
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}