package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_Skill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_Skill(int skillId, int argument) {
        this.byteArray.writeByte(skillId);
        this.byteArray.writeByte(argument);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 40;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}