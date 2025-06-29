package org.transformice.packets.send.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_PlayerAction implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_PlayerAction(int sessionId, int actionType, String flag, boolean isFromLua) {
        this.byteArray.writeInt(sessionId);
        this.byteArray.writeByte(actionType);
        if(!flag.isEmpty())
            this.byteArray.writeString(flag);
        this.byteArray.writeBoolean(isFromLua);
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}