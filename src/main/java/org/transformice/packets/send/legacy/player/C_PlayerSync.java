package org.transformice.packets.send.legacy.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerSync implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerSync(int sessionId, boolean spawn_initial_objects) {
        this.byteArray.writeString(String.valueOf(sessionId), false);
        if(spawn_initial_objects) {
            this.byteArray.writeByte(1);
            this.byteArray.writeString("", false);
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 21;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}