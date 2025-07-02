package org.transformice.packets.send.legacy.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_OldExceptionNotify implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_OldExceptionNotify(String playerName, String error) {
        this.byteArray.writeString(playerName, false);
        this.byteArray.writeByte(1);
        this.byteArray.writeString(error, false);
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 25;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}