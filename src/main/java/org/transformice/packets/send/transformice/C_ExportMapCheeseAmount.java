package org.transformice.packets.send.transformice;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_ExportMapCheeseAmount implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ExportMapCheeseAmount() {
        this.byteArray.writeUnsignedShort(Application.getPropertiesInfo().map_editor_cheese_amount);
    }

    @Override
    public int getC() {
        return 100;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}