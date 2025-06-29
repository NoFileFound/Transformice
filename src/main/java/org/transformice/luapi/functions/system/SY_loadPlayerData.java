package org.transformice.luapi.functions.system;

// Imports
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class SY_loadPlayerData extends VarArgFunction {
    private final Room room;

    public SY_loadPlayerData(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.loadPlayerData() function.
     * @param args The arguments.
     * @return True if file is loaded successfully or else False.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.loadPlayerData : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                if (this.room.getPlayers().containsKey(playerName)) {
                    String fileName;
                    if(this.room.getMinigameName().isEmpty()) {
                        fileName = "./data/" + this.room.luaAdmin.getPlayerName() + ".json";
                    } else {
                        fileName = "./data/module_" + this.room.getMinigameName() + "/" + playerName + ".json";
                    }
                    File file = new File(fileName);
                    if (file.exists()) {
                        try {
                            String content = Files.readString(Paths.get(file.toURI()), StandardCharsets.UTF_8);
                            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
                            if (json.has("playerData") && json.get("playerData").isJsonObject()) {
                                JsonObject playerData = json.getAsJsonObject("playerData");
                                if (playerData.has(playerName)) {
                                    JsonElement playerJson = playerData.get(playerName);
                                    this.room.luaApi.callEvent("eventPlayerDataLoaded", playerName, playerJson.getAsString());
                                    return TRUE;
                                }
                            }
                        } catch (JsonSyntaxException | IOException error) {
                            throw new LuaError(error.getMessage());
                        }
                    }
                }
            }
        }
        return FALSE;
    }
}