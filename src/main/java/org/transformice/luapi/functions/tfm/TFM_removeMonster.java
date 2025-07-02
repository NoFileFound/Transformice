package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.login.C_DespawnMonster;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_removeMonster extends VarArgFunction {
    private final Room room;

    public TFM_removeMonster(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removeMonster() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.removeMonster : argument 1 can't be NIL."));
            } else {
                int monsterId = args.toint(1);
                String playerName = args.tojstring(2);
                if(playerName.equals("nil")) {
                    this.room.sendAll(new C_DespawnMonster(monsterId));
                } else {
                    if(this.room.getPlayers().get(playerName) != null) {
                        this.room.getPlayers().get(playerName).sendPacket(new C_DespawnMonster(monsterId));
                    }
                }
            }
        }
        return NIL;
    }
}