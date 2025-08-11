package org.transformice.luapi;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.transformice.Application;
import org.transformice.Room;

// Lua api
import org.transformice.luapi.functions.LA_print;
import org.transformice.luapi.functions.debug.LUA_TABLE_DBG;
import org.transformice.luapi.functions.os.LUA_TABLE_OS;
import org.transformice.luapi.functions.system.LUA_TABLE_SY;
import org.transformice.luapi.functions.tfm.LUA_TABLE_TFM;
import org.transformice.luapi.functions.ui.LUA_TABLE_UI;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class LuaApiLib extends TwoArgFunction  {
    private final Room room;
    private final List<Object[]> pendent = new ArrayList<>();

    public LuaApiLib(Room room) {
        this.room = room;
    }

    @Override
    public LuaValue call(LuaValue name, LuaValue table) {
        table.set("print", new LA_print(this.room));
        table.load(new LUA_TABLE_DBG(this.room));
        table.load(new LUA_TABLE_OS());
        table.load(new LUA_TABLE_UI(this.room));
        table.load(new LUA_TABLE_SY(this.room));
        table.load(new LUA_TABLE_TFM(this.room));
        return NIL;
    }

    /**
     * Calls the pendent events.
     */
    public void callPendentEvents() {
        for (Object[] object : this.pendent) {
            this.callEvent((String) object[0], (Object[]) object[1]);
        }

        this.pendent.clear();
        this.room.startLuaLoop();
    }

    /**
     * Calls the given event.
     * @param event The event name.
     * @param args The event arguments.
     */
    public void callEvent(String event, Object... args) {
        if (!this.room.isFinishedLuaScript) {
            this.pendent.add(new Object[] {event, args});
        } else {
            if (this.room.luaMinigame != null && this.room.luaMinigame.get(event) != LuaValue.NIL && this.room.luaMinigame.get(event).isfunction()) {
                this.callEvent(event, this.room.luaMinigame.get(event).checkfunction(), args);
            }
        }
    }

    /**
     * Searches if string contains any html url, tags or clickque event.
     * @param text The given string.
     * @return 1 if string contains url, 2 if contains clickque event, 3 if contains img tag or else 0.
     */
    public int filterHtml(String text) {
        Matcher matcher = Pattern.compile("href=([\"'])(.*?)([\"'])").matcher(text);
        if (matcher.find()) {
            String url = matcher.group(2);
            if (url.contains("http")) {
                return 1;
            } else if (!url.startsWith("event:")) {
                return 2;
            }
        }

        if (text.contains("<img")) {
            return 3;
        }

        return 0;
    }

    /**
     * Converts #RRBBGG (hex color) to int.
     * @param hex The given hex color.
     * @return Integer representation of the hex color or -1 if its invalid color.
     */
    public int hexColorToInt(String hex) {
        if (hex == null || hex.length() != 7 || !hex.startsWith("#")) {
            return -1;
        }
        try {
            return Integer.parseInt(hex.substring(1), 16);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Calls the given event.
     * @param eventName The event name.
     * @param event The event function.
     * @param args The event arguments.
     */
    private void callEvent(String eventName, LuaFunction event, Object... args) {
        if (this.room.luaMinigame != null && event != null) {
            new Thread(() -> {
                LuaValue[] luaArgs = Arrays.stream(args).map(arg -> arg instanceof Integer ? LuaValue.valueOf((Integer) arg) : arg instanceof String ? LuaValue.valueOf((String) arg) : arg instanceof Boolean ? LuaValue.valueOf((Boolean) arg) : arg instanceof Long ? LuaInteger.valueOf((Long) arg) : arg).toArray(LuaValue[]::new);
                long startTime = System.currentTimeMillis();
                long endTime;
                try {
                    this.room.luaDebugLib.setTimeOut(!this.room.isLuaMinigame() ? 4000 : -1, false);
                    event.invoke(luaArgs);
                    endTime = System.currentTimeMillis() - startTime;

                } catch (LuaError error) {
                    Object[] errorInfo = new Object[2];
                    int lineNumber = -1;
                    String message = error.getMessage();
                    if (message != null) {
                        String[] parts = message.split(":");
                        if (parts.length >= 2) {
                            try {
                                lineNumber = Integer.parseInt(parts[1].trim());
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                    errorInfo[0] = lineNumber;
                    errorInfo[1] = message != null ? message : "";
                    if (this.room.luaAdmin != null) {
                        if (message != null && message.contains("RuntimeException")) {
                            this.room.luaAdmin.sendPacket(new C_LuaMessage("<V>[" + this.room.getRoomName() + "]</V> Runtime Error : " + this.room.luaAdmin.getPlayerName() + ".functions:" + (lineNumber == -1 ? "" : (lineNumber + ":")) + " Lua destroyed : Runtime can't exceed 40 ms in 4 seconds."));
                        } else {
                            this.room.luaAdmin.sendPacket(new C_LuaMessage("<V>[" + this.room.getRoomName() + "]</V> Runtime Error : " + this.room.luaAdmin.getPlayerName() + ".functions:" + (lineNumber == -1 ? "" : (lineNumber + ":")) + " " + errorInfo[1]));
                        }
                    } else {
                        if (message != null && message.contains("RuntimeException")) {
                            Application.getLogger().error("Minigame #{}: Runtime Error : {} Lua destroyed : Runtime can't exceed 40 ms in 4 seconds.", this.room.getMinigameName(), lineNumber == -1 ? "" : (lineNumber + ":"));
                        } else {
                            Application.getLogger().error("Minigame #{}: Runtime Error : {} {}", this.room.getMinigameName(), lineNumber == -1 ? "" : (lineNumber + ":"), errorInfo[1]);
                        }
                    }

                    this.room.stopLuaScript(true);
                    return;
                }

                if (!this.room.disableEventLog && this.room.luaAdmin != null) {
                    this.room.luaAdmin.sendPacket(new C_LuaMessage("Lua " + eventName + " executed in " + endTime + " ms."));
                }
            }).start();
        }
    }
}