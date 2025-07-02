package org.transformice.packets.send.payment;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PurchaseFailure implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PurchaseFailure(String purchaseToken) {
        this.byteArray.writeString(purchaseToken);
    }

    @Override
    public int getC() {
        return 12;
    }

    @Override
    public int getCC() {
        return 3;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}