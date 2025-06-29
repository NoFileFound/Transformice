package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_SetAIEMode;

public final class TFM_setAieMode extends VarArgFunction {
    private final Room room;

    public TFM_setAieMode(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setAieMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setAieMode : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setAieMode : argument 2 can't be NIL."));
            } else {
                boolean enable = args.toboolean(1);
                int sensibility = args.toint(2);
                String playerName = args.tojstring(3);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).sendPacket(new C_SetAIEMode(enable, sensibility));
                } else {
                    this.room.sendAll(new C_SetAIEMode(enable, sensibility));
                }
            }
        }
        return NIL;
    }
}