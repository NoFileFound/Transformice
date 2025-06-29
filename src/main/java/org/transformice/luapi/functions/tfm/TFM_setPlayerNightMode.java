package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_EnableNightMode;

public final class TFM_setPlayerNightMode extends VarArgFunction {
    private final Room room;

    public TFM_setPlayerNightMode(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerNightMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setPlayerNightMode : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                boolean enable = args.isnil(2) || args.toboolean(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).sendPacket(new C_EnableNightMode(enable));
                }
            }
        }
        return NIL;
    }
}