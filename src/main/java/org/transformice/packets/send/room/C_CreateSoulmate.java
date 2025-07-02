package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_CreateSoulmate implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_CreateSoulmate(boolean enable, int playerSessionId1, int playerSessionId2) {
        this.byteArray.writeBoolean(enable);
        this.byteArray.writeInt(playerSessionId1);
        if(enable) {
            this.byteArray.writeInt(playerSessionId2);
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 48;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}