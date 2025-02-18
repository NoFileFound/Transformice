package org.transformice.packets.recv.language;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.language.C_LanguageList;

@SuppressWarnings("unused")
public final class S_LanguageList implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendPacket(new C_LanguageList(client.getCountryLangue()));
    }

    @Override
    public int getC() {
        return 176;
    }

    @Override
    public int getCC() {
        return 2;
    }
}