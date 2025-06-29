package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ChangeLookNPC implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ChangeLookNPC(String npcId, String npcLook) {
        this.byteArray.writeString(npcId);
        this.byteArray.writeString(npcLook);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 49;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}