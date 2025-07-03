package org.transformice.packets.recv.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

@SuppressWarnings("unused")
public final class S_ExportMap implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if (!client.getRoom().isEditeur()) return;

        /// TODO: Implement S_ExportMap
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 18;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}