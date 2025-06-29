package org.transformice.packets.send.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SayPopup implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SayPopup(boolean show, String message) {
        this.byteArray.writeByte((show) ? 1 : 2);
        if(show) {
            this.byteArray.writeString(message);
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 11;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}