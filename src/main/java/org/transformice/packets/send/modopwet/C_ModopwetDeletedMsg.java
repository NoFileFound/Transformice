package org.transformice.packets.send.modopwet;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_ModopwetDeletedMsg implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_ModopwetDeletedMsg(String playerName, String deleter) {
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(deleter);
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 7;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}