package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_PlayerSetCollision;

public final class TFM_setPlayerCollision extends VarArgFunction {
    private final Room room;

    public TFM_setPlayerCollision(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerCollision() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setPlayerCollision : argument 1 can't be NIL."));
            } if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setPlayerCollision : argument 2 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                int collisionType = args.toint(2);
                int categoryBits = args.toint(3);
                int maskBits = args.toint(4);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.sendAll(new C_PlayerSetCollision(this.room.getPlayers().get(playerName).getSessionId(), collisionType, categoryBits, maskBits));
                }
            }
        }
        return NIL;
    }
}