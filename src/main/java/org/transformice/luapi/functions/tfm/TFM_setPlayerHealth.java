package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.login.C_SetPlayerHealth;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_setPlayerHealth extends VarArgFunction {
    private final Room room;

    public TFM_setPlayerHealth(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerHealth() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setPlayerHealth : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                int health = args.isnil(2) ? 3 : args.toint(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).playerHealth = health;
                    this.room.getPlayers().get(playerName).sendPacket(new C_SetPlayerHealth(health));
                }
            }
        }
        return NIL;
    }
}