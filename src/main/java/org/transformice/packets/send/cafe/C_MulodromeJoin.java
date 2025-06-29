package org.transformice.packets.send.cafe;

// Imports
import org.bytearray.ByteArray;
import org.transformice.packets.SendPacket;

public final class C_MulodromeJoin implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    public C_MulodromeJoin(int team, int position, int playerSession, String playerName, String tribeName) {
        this.byteArray.writeByte(team);
        this.byteArray.writeByte(position);
        this.byteArray.writeInt(playerSession);
        this.byteArray.writeString(playerName);
        this.byteArray.writeString(tribeName);
    }

    @Override
    public int getC() {
        return 30;
    }

    @Override
    public int getCC() {
        return 15;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}