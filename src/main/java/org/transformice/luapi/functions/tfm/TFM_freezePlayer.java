package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.room.C_IcedMouseSkill;

public final class TFM_freezePlayer extends VarArgFunction {
    private final Room room;

    public TFM_freezePlayer(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.freezePlayer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.freezePlayer : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                boolean freeze = args.toboolean(2);
                boolean displayIce = args.toboolean(3);
                this.room.sendAll(new C_IcedMouseSkill(this.room.getPlayers().get(playerName) != null ? this.room.getPlayers().get(playerName).getSessionId() : -1, freeze, displayIce));
            }
        }
        return NIL;
    }
}