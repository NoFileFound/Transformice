package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_PlayerGetCheese;

public final class TFM_removeCheese extends VarArgFunction {
    private final Room room;

    public TFM_removeCheese(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removeCheese() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.removeCheese : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).cheeseCount = 0;
                    this.room.sendAll(new C_PlayerGetCheese(this.room.getPlayers().get(playerName).getSessionId(), 0));
                }
            }
        }

        return NIL;
    }
}