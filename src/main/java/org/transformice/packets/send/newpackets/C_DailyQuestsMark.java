package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DailyQuestsMark implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DailyQuestsMark(boolean enable) {
        this.byteArray.writeBoolean(enable);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}