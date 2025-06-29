package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SpawnPet implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SpawnPet(int sessionId, int petType) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeUnsignedByte(petType);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 70;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}