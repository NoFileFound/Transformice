package org.transformice.packets.recv.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.legacy.editor.C_InitMapEditor;

@SuppressWarnings("unused")
public final class S_LeaveEditor implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        client.sendOldPacket(new C_InitMapEditor(0));
        client.sendEnterRoom(client.getServer().getRecommendedRoom(""), "");
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 26;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}