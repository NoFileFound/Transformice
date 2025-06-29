package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanEnableSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanEnableSkill(int skill_id, int argument) {
        this.byteArray.writeUnsignedByte(skill_id);
        this.byteArray.writeUnsignedByte(argument);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}