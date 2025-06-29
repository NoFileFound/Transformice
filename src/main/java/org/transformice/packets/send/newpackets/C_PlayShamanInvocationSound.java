package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayShamanInvocationSound implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayShamanInvocationSound(int objId) {
        this.byteArray.writeByte(objId);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}