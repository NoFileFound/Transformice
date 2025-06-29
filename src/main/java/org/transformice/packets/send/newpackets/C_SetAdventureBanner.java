package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_SetAdventureBanner implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SetAdventureBanner() {
        this.byteArray.writeInt128(Application.getPropertiesInfo().event.banner_id);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 31;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}