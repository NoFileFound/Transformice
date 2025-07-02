package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.login.C_SetMonsterSpeed;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_setMonsterSpeed extends VarArgFunction {
    private final Room room;

    public TFM_setMonsterSpeed(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setMonsterSpeed() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setMonsterSpeed : argument 1 can't be NIL."));
            } else {
                int monsterId = args.toint(1);
                int speed = args.toint(2);
                String targetPlayer = args.tojstring(3);
                if(targetPlayer.equals("nil")) {
                    this.room.sendAll(new C_SetMonsterSpeed(monsterId, speed));
                } else {
                    if(this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).sendPacket(new C_SetMonsterSpeed(monsterId, speed));
                    }
                }
            }
        }
        return NIL;
    }
}