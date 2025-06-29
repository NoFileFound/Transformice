package org.transformice.packets.send.room;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_EvolutionSkill implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_EvolutionSkill(int session, int arg) {
        this.byteArray.writeInt(session);
        this.byteArray.writeUnsignedByte(arg);
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 38;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}