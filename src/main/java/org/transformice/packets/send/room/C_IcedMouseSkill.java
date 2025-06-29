package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_IcedMouseSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_IcedMouseSkill(int sessionId, boolean enable, boolean showIce) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeBoolean(enable);
        this.byteArray.writeBoolean(showIce);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 34;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}