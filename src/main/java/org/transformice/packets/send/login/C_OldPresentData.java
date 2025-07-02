package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_OldPresentData implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OldPresentData(String presentData) {
        this.byteArray.writeString(presentData);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}