package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

@SuppressWarnings("unused")
public final class S_UploadModuleImageResult implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        /// TODO: Implement
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 29;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}