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

public final class SY_saveFile extends VarArgFunction {
    private final Room room;
    private long saveFileTime;

    public SY_saveFile(Room room) {
        this.room = room;
    }

    /**
     * Invokes the system.saveFile() function.
     * @param args The arguments.
     * @return True if file is saved successfully or else False.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("system.saveFile : argument 1 can't be NIL."));
            } else if (this.saveFileTime > System.currentTimeMillis()) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("You can't call this function [system.saveFile] more than once per 10 minutes."));
            } else {
                String data = args.tojstring(1);
                int fileNumber = args.toint(2);
                if (data.length() <= 64000 && fileNumber >= 0 && fileNumber <= 99) {
                    try {
                        String fileName;
                        if(this.room.getMinigameName().isEmpty()) {
                            fileName = "./data/" + this.room.luaAdmin.getPlayerName() + ".json";
                        } else {
                            fileName = "./data/module_" + this.room.getMinigameName() + "/" + this.room.luaAdmin.getPlayerName() + ".json";
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

                        json.addProperty(String.valueOf(fileNumber), data);
                        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
                            Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
                            writer.write(gson.toJson(json));
                        }

                        this.room.luaApi.callEvent("eventFileSaved", fileNumber);
                        this.saveFileTime = System.currentTimeMillis() + 600_000;
                        return TRUE;

                    } catch (JsonSyntaxException | IOException error) {
                        throw new LuaError(error.getMessage());
                    }
                }
            }
        }
        return FALSE;
    }
}