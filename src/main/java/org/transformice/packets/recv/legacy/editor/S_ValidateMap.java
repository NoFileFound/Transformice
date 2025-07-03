package org.transformice.packets.recv.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.legacy.editor.C_InitMapEditor;

@SuppressWarnings("unused")
public final class S_ValidateMap implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.getRoom().isEditeur()) return;

        String mapXmlCode = new String(data.readBytes(data.getLength()));
        if(Utils.checkValidXML(mapXmlCode)) {
            client.sendOldPacket(new C_InitMapEditor(2));
            client.getRoom().isMapEditorMapValidating = true;
            client.getRoom().isMapEditorMapValidated = false;
            client.getRoom().setMapEditorXml(mapXmlCode);
            client.getRoom().changeMap();
        }
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}