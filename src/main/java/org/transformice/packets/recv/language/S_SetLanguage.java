package org.transformice.packets.recv.language;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.language.C_SetLanguage;

@SuppressWarnings("unused")
public final class S_SetLanguage implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        String language = data.readString().toUpperCase(); // language

        client.playerCommunity = language;
        client.sendPacket(new C_SetLanguage(language));
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}