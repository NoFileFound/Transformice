package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DespawnNPC implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DespawnNPC(String npcId) {
        this.byteArray.writeString(npcId);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 50;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}