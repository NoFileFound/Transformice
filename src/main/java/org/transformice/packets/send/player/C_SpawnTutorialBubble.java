package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_SpawnTutorialBubble implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_SpawnTutorialBubble(int sessionId, int actionType) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(actionType);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 13;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}