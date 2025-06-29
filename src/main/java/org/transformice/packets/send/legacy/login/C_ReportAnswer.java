package org.transformice.packets.send.legacy.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ReportAnswer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ReportAnswer(String playerName) {
        this.byteArray.writeString(playerName, false);
    }

    @Override
    public int getC() {
        return 26;
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