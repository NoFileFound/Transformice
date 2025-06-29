package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_RockPaperScissorsAction implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_RockPaperScissorsAction(int playerSession1, int num, int playerSession2, int num2) {
        this.byteArray.writeInt(playerSession1);
        this.byteArray.writeByte(num);
        this.byteArray.writeInt(playerSession2);
        this.byteArray.writeByte(num2);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 5;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}