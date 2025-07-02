package org.transformice.packets.send.payment;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PurchaseOpenUrl implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PurchaseOpenUrl(String purchaseUrl) {
        this.byteArray.writeString(purchaseUrl);
    }

    @Override
    public int getC() {
        return 12;
    }

    @Override
    public int getCC() {
        return 40;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}