package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SayPopup implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SayPopup(String data) {
        this.byteArray.writeByte(1);
        this.byteArray.writeString(data);
    }

    public C_SayPopup() {
        this.byteArray.writeByte(2);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 34;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}