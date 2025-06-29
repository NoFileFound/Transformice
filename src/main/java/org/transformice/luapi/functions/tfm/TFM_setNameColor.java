package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.lua.C_SetNicknameColor;

public final class TFM_setNameColor extends VarArgFunction {
    private final Room room;

    public TFM_setNameColor(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setNameColor() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setNameColor : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setNameColor : argument 2 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                int color = args.toint(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.sendAll(new C_SetNicknameColor(this.room.getPlayers().get(playerName).getSessionId(), color));
                }
            }
        }
        return NIL;
    }
}