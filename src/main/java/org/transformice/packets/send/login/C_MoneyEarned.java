package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MoneyEarned implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MoneyEarned(int cheeses, int fraises) {
        this.byteArray.writeInt(cheeses);
        this.byteArray.writeInt(fraises);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}