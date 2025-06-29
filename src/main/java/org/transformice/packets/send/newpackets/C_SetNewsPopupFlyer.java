package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_SetNewsPopupFlyer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetNewsPopupFlyer() {
        this.byteArray.writeString(Application.getPropertiesInfo().flyerName);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 35;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}