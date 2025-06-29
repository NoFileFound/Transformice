package org.transformice.packets.recv.room;

// Imports
import java.util.List;
import org.bytearray.ByteArray;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.transformice.Client;
import org.transformice.libraries.Pair;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.legacy.C_Totem;
import org.transformice.packets.send.newpackets.C_PlayShamanInvocationSound;

@SuppressWarnings("unused")
public final class S_ShamanSpawnObject implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int roundCode = data.readByte();
        if(roundCode == client.getRoom().getLastRoundId()) {
            int objectId = data.readInt128();
            int shamanObjectId = data.readInt128();
            int posX = data.readInt128();
            int posY = data.readInt128();
            int angle = data.readInt128();
            int velocityX = data.readInt128();
            int velocityY = data.readInt128();
            boolean miceCollidable = data.readBoolean();
            boolean spawnedByPlayer = data.readBoolean();
            int shamanSessionId = data.readInt128();
            if ((!client.isShaman || shamanSessionId != client.getSessionId()) && spawnedByPlayer) {
                return;
            }

            if(spawnedByPlayer)
                client.sendPacket(new C_PlayShamanInvocationSound(shamanObjectId));

            if(client.getRoom().isTotem()) {
                client.tempTotemInfo = new Pair<>(client.tempTotemInfo.getFirst() + 1, client.tempTotemInfo.getSecond() + "#2#" + String.join("\u0001", String.valueOf(shamanObjectId), String.valueOf(posX), String.valueOf(posY), String.valueOf(angle), String.valueOf(velocityX), String.valueOf(velocityY), miceCollidable ? "1" : "0"));
                client.sendTotemUsedCount();
                return;
            }

            if (shamanObjectId == 44) {
                if(!client.isUsedTotem) {
                    client.sendOldPacket(new C_Totem(client.getSessionId(), posX, posY, (String)client.getAccount().getTotemInfo()[1]));
                    client.isUsedTotem = true;
                }
            }

            List<Integer> itemColors = client.getShamanItemCustomization(shamanObjectId);
            byte[] colorBytes = new byte[itemColors.size()];
            for (int i = 0; i < itemColors.size(); i++) {
                colorBytes[i] = itemColors.get(i).byteValue();
            }

            client.getRoom().sendPlaceObject(objectId, shamanObjectId, posX, posY, angle, velocityX, velocityY, miceCollidable, false, colorBytes, client, false);
            client.getParseSkillsInstance().handleSkill(objectId, shamanObjectId, posX, posY, angle);
            if (client.getRoom().luaMinigame != null) {
                LuaTable colors = new LuaTable();
                for (int i = 0; i < itemColors.size(); i++) {
                    colors.set(String.valueOf(i), String.format("%06X", (0xFFFFFF & itemColors.get(i))));
                }

                LuaTable item = new LuaTable();
                item.set("id", objectId);
                item.set("type", shamanObjectId);
                item.set("baseType", (shamanObjectId > 99 ? shamanObjectId / 100 : shamanObjectId));
                item.set("angle", angle);
                item.set("x", posX);
                item.set("y", posY);
                item.set("vx", velocityX);
                item.set("vy", velocityY);
                item.set("ghost", LuaBoolean.valueOf(!miceCollidable));
                item.set("colors", colors);
                try {
                    if (client.getRoom().luaMinigame.get("tfm").get("get").get("room").get("objectList").istable()) {
                        client.getRoom().luaMinigame.get("tfm").get("get").get("room").get("objectList").set(String.valueOf(objectId), item);
                    }
                } catch (LuaError _) {}

                client.getRoom().luaApi.callEvent("eventSummoningEnd", client.getPlayerName(), shamanObjectId, posX, posY, angle, item);
            }
        }
    }

    @Override
    public int getC() {
        return 5;
    }

    @Override
    public int getCC() {
        return 20;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}