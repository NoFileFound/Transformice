package org.transformice.packets.send.newpackets.sonar;

// Imports
import org.bytearray.ByteArray;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.SendPacket;

public final class C_StartSonar implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_StartSonar(int playerSession, boolean enable) {
        this.byteArray.writeInt(playerSession);
        this.byteArray.writeBoolean(enable);
        this.byteArray.writeShort(enable ? (short)SrcRandom.RandomNumber(1, 65655) : 0);
    }

    @Override
    public int getC() {
        return 145;
    }

    @Override
    public int getCC() {
        return 174;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}