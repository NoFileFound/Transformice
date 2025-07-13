package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_DecoratePlayerList implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_DecoratePlayerList(String leftImageName, int leftGatheredCheese, int leftTextColor, String rightImageName, int rightGatheredCheese, int rightTextColor) {
        this.byteArray.writeInt128(1);
        this.byteArray.writeString(leftImageName);
        this.byteArray.writeString(String.valueOf(leftGatheredCheese));
        this.byteArray.writeInt128(leftTextColor);
        this.byteArray.writeString(rightImageName);
        this.byteArray.writeString(String.valueOf(rightGatheredCheese));
        this.byteArray.writeInt128(rightTextColor);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 26;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}