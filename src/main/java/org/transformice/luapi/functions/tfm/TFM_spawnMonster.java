package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.login.C_SpawnMonster;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_spawnMonster extends VarArgFunction {
    private final Room room;

    public TFM_spawnMonster(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.spawnMonster() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.spawnMonster : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.spawnMonster : argument 2 can't be NIL."));
            } else {
                int monsterId = args.toint(1);
                String monsterType = args.tojstring(2);
                int x = args.toint(3);
                int y = args.toint(4);
                String targetPlayer = args.tojstring(5);
                if(targetPlayer.equals("nil")) {
                    this.room.sendAll(new C_SpawnMonster(monsterId, x, y, monsterType));
                } else {
                    if(this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).sendPacket(new C_SpawnMonster(monsterId, x, y, monsterType));
                    }
                }
            }
        }
        return NIL;
    }
}