package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.room.C_AddBonus;

public final class TFM_addBonus extends VarArgFunction {
    private final Room room;

    public TFM_addBonus(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addBonus() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addBonus : argument 1 can't be NIL."));
            } else {
                int bonusType = args.toint(1);
                int x = args.toint(2);
                int y = args.toint(3);
                int id = args.toint(4);
                int angle = args.toint(5);
                boolean visible = args.toboolean(6);
                String playerName = args.tojstring(7);
                if(args.isnil(7)) {
                    this.room.sendAll(new C_AddBonus(bonusType, x, y, angle, id, visible));
                } else {
                    if(this.room.getPlayers().get(playerName) != null) {
                        this.room.getPlayers().get(playerName).sendPacket(new C_AddBonus(bonusType, x, y, angle, id, visible));
                    }
                }
            }
        }
        return NIL;
    }
}