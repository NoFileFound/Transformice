package org.transformice.packets.send.player.sync;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerCrouch implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerCrouch(int sessionId, int action_type) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(action_type);
        this.byteArray.writeBoolean(false);
    }

    @Override
    public int getC() {
        return 4;
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