package org.transformice.luapi.functions.system;

// Imports
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class SY_savePlayerData extends VarArgFunction {
    private final Room room;

    public SY_savePlayerData(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.savePlayerData() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.savePlayerData : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.savePlayerData : argument 2 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                String data = args.tojstring(2);
                if (data.length() <= 64000) {
                    if (this.room.getPlayers().containsKey(playerName)) {
                        try {
                            String fileName;
                            if(this.room.getMinigameName().isEmpty()) {
                                fileName = "./data/" + this.room.luaAdmin.getPlayerName() + ".json";
                            } else {
                                fileName = "./data/module_" + this.room.getMinigameName() + "/" + playerName + ".json";
                            }
                            File file = new File(fileName);
                            if (!file.exists()) {
                                file.createNewFile();
                                try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
                                    writer.write("{}");
                                }
                            }

                            JsonObject json;
                            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                                json = JsonParser.parseReader(reader).getAsJsonObject();
                            }

                            JsonObject playerData;
                            if (json.has("playerData") && json.get("playerData").isJsonObject()) {
                                playerData = json.getAsJsonObject("playerData");
                            } else {
                                playerData = new JsonObject();
                                json.add("playerData", playerData);
                            }

                            playerData.addProperty(playerName, data);
                            try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
                                Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
                                writer.write(gson.toJson(json));
                            }
                            this.room.luaApi.callEvent("eventPlayerDataSaved", playerName);
                        } catch (JsonSyntaxException | IOException error) {
                            throw new LuaError(error.getMessage());
                        }
                    }
                }
            }
        }
        return NIL;
    }
}