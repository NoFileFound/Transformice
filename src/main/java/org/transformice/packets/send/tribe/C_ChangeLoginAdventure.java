package org.transformice.packets.send.tribe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.packets.SendPacket;

public final class C_ChangeLoginAdventure implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ChangeLoginAdventure() {
        this.byteArray.writeUnsignedByte(1);
        this.byteArray.writeUnsignedByte(Application.getPropertiesInfo().event.banner_id);
        this.byteArray.writeBoolean(true); /// Shows the adventure banner id.
        this.byteArray.writeBoolean(true); /// A new adventure begins
    }

    @Override
    public int getC() {
        return 16;
    }

    @Override
    public int getCC() {
        return 9;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}