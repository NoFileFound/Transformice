package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_MonsterSynchronization implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        /// TODO: Implement S_MonsterSynchronization
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}