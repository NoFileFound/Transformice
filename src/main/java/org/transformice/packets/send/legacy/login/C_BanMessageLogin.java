package org.transformice.packets.send.legacy.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_BanMessageLogin implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_BanMessageLogin(String reason) {
        this.byteArray.writeString(reason, false);
    }

    public C_BanMessageLogin(long duration, String reason) {
        this.byteArray.writeString(String.valueOf(duration), false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(reason, false);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 18;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}