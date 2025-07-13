package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.room.C_AddCollectible;

public final class TFM_addCollectible extends VarArgFunction {
    private final Room room;

    public TFM_addCollectible(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addCollectible() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addCollectible : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addCollectible : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addCollectible : argument 3 can't be NIL."));
            } else if (args.isnil(4)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addCollectible : argument 4 can't be NIL."));
            } else {
                int collectibleId = args.toint(1);
                int collectibleType = args.toint(2);
                int x = args.toint(3);
                int y = args.toint(4);
                String playerName = args.tojstring(5);
                if(args.isnil(5)) {
                    this.room.sendAll(new C_AddCollectible(-1, collectibleId, collectibleType, x, y));
                } else {
                    if(this.room.getPlayers().get(playerName) != null) {
                        this.room.getPlayers().get(playerName).sendPacket(new C_AddCollectible(-1, collectibleId, collectibleType, x, y));
                    }
                }
            }
        }
        return NIL;
    }
}