package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_AppearAIEDeathBubble implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_AppearAIEDeathBubble(int sessionId) {
        this.byteArray.writeInt(sessionId);
    }

    @Override
    public int getC() {
        return 8;
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