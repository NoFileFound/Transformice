package org.transformice.packets.recv.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.MapEditor;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.legacy.editor.C_EditorMessage;
import org.transformice.packets.send.legacy.editor.C_InitMapEditor;
import org.transformice.packets.send.legacy.editor.C_MapExported;

@SuppressWarnings("unused")
public final class S_ExportMap implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if (!client.getRoom().isEditeur()) return;

        boolean isTribeHouse = data.getLength() > 0;
        if(!client.hasStaffPermission("MapCrew", "MapEditor")) {
            if((isTribeHouse && client.getAccount().getShopCheeses() < 5) || client.getAccount().getShopCheeses() <= Application.getPropertiesInfo().map_editor_cheese_amount) {
                client.sendOldPacket(new C_EditorMessage());
                return;
            }

            client.getAccount().setShopCheeses(client.getAccount().getShopCheeses() - (isTribeHouse ? 5 : Application.getPropertiesInfo().map_editor_cheese_amount));
        }

        int mapCode;
        if(client.getRoom().EMapCodeLoaded != 0) {
            mapCode = client.getRoom().EMapCodeLoaded;
            MapEditor map = DBUtils.findMapByCode(mapCode);
            map.setMapXML(client.getRoom().getMapEditorXml());
            map.save();
        } else {
            MapEditor map = new MapEditor(client.getRoom().getMapEditorXml(), client.getPlayerName(), isTribeHouse);
            mapCode = map.getMapCode();
            map.save();
        }

        client.sendOldPacket(new C_InitMapEditor(0));
        client.sendEnterRoom(client.getServer().getRecommendedRoom(""), "");
        client.sendOldPacket(new C_MapExported(mapCode));
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