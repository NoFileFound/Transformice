package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_LoginQueue implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_LoginQueue(int players) {
        this.byteArray.writeUnsignedInt(players);
    }

    @Override
    public int getC() {
        return 28;
    }

    @Override
    public int getCC() {
        return 61;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}