package org.transformice.packets.send.legacy.player;

// Imports
import org.transformice.packets.SendPacket;

public final class C_PlayerSaveRemainingNotification implements SendPacket {
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
        return new byte[0];
    }
}