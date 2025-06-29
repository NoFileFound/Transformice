package org.transformice.packets.send.tribulle;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SwitchNewTribulle implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SwitchNewTribulle() {
        this.byteArray.writeBoolean(true);
    }

    @Override
    public int getC() {
        return 60;
    }

    @Override
    public int getCC() {
        return 4;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}