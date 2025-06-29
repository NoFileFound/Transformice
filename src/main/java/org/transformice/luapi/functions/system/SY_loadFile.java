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

public final class SY_loadFile extends VarArgFunction {
    private final Room room;
    private long loadFileTime;

    public SY_loadFile(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.loadFile() function.
     * @param args The arguments.
     * @return True if file is loaded successfully or else False.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (this.loadFileTime > System.currentTimeMillis()) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("You can't call this function [system.loadFile] more than once per 10 minutes."));
            } else {
                int fileNumber = args.toint(1);
                if (fileNumber >= 0 && fileNumber <= 99) {
                    String fileName;
                    if(this.room.getMinigameName().isEmpty()) {
                        fileName = "./data/" + this.room.luaAdmin.getPlayerName() + ".json";
                    } else {
                        fileName = "./data/module_" + this.room.getMinigameName() + "/" + this.room.luaAdmin.getPlayerName() + ".json";
                    }

                    File file = new File(fileName);
                    if (file.exists()) {
                        try {
                            String content = Files.readString(Paths.get(file.toURI()), StandardCharsets.UTF_8);
                            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
                            String key = String.valueOf(fileNumber);
                            if (json.has(key)) {
                                JsonElement value = json.get(key);
                                this.room.luaApi.callEvent("eventFileLoaded", fileNumber, value.getAsString());
                                this.loadFileTime = System.currentTimeMillis() + 600000;
                                return TRUE;
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