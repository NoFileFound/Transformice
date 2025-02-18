package org.transformice.packets.send.legacy;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_BanMessage implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_BanMessage(String reason) {
        this.byteArray.writeString(reason, false);
    }

    public C_BanMessage(long duration, String reason) {
        this.byteArray.writeString(String.valueOf(duration), false).writeByte((byte)1).writeString(reason, false);
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