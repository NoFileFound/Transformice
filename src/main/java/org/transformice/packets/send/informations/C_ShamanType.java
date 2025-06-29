package org.transformice.packets.send.informations;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ShamanType implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ShamanType(int shamanMode, boolean canDivine, int shamanColor, boolean noSkill) {
        this.byteArray.writeByte(shamanMode);
        this.byteArray.writeBoolean(canDivine);
        this.byteArray.writeInt(shamanColor);
        this.byteArray.writeBoolean(noSkill);
    }

    @Override
    public int getC() {
        return 28;
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