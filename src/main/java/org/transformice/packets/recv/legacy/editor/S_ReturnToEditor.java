package org.transformice.packets.recv.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.legacy.editor.C_InitMapEditor;

@SuppressWarnings("unused")
public final class S_ReturnToEditor implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.getRoom().isMapEditorMapValidating = false;
        client.sendOldPacket(new C_InitMapEditor(1));
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 14;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}