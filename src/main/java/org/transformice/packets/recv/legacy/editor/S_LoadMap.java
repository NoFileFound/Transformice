package org.transformice.packets.recv.legacy.editor;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.database.DBUtils;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.legacy.editor.C_LoadMap;
import org.transformice.packets.send.legacy.editor.C_LoadMapResult;

@SuppressWarnings("unused")
public final class S_LoadMap implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        if(!client.getRoom().isEditeur()) return;

        String mapCode = new String(data.readBytes(data.getLength())).replace("@", "");
        try {
            int code = Integer.parseInt(mapCode);
            var mapInfo = DBUtils.findMapByCode(code);
            boolean hasPerm = client.hasStaffPermission("MapCrew", "MapEditor");
            if(mapInfo == null) {
                client.sendOldPacket(new C_LoadMapResult());
                return;
            }

            if(!mapInfo.getMapAuthor().equals(client.getPlayerName()) && !hasPerm) {
                client.sendOldPacket(new C_LoadMapResult());
                return;
            }

            if(mapInfo.getMapCategory() != 22 && mapInfo.getMapCategory() != 0 && !hasPerm) {
                client.sendOldPacket(new C_LoadMapResult());
                return;
            }

            client.sendOldPacket(new C_LoadMap(mapInfo.getMapXML(), mapInfo.getMapYesVotes(), mapInfo.getMapNoVotes(), (hasPerm) ? mapInfo.getMapCategory() : 0));
        } catch (NumberFormatException ignored) {
            client.sendOldPacket(new C_LoadMapResult());
        }
    }

    @Override
    public int getC() {
        return 14;
    }

    @Override
    public int getCC() {
        return 6;
    }

    @Override
    public boolean isLegacyPacket() {
        return true;
    }
}