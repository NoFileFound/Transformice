package org.transformice.packets.send.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_NewPlayer implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_NewPlayer(ByteArray playerData) {
        this.byteArray.writeBytes(playerData.toByteArray());
        this.byteArray.writeBoolean(false);
        this.byteArray.writeBoolean(false);
    }

    public C_NewPlayer(ByteArray playerData, boolean skipAnim, boolean refreshMenu) {
        this.byteArray.writeBytes(playerData.toByteArray());
        this.byteArray.writeBoolean(skipAnim);
        this.byteArray.writeBoolean(refreshMenu);
    }

    @Override
    public int getC() {
        return 144;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}