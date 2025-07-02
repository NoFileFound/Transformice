package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.player.C_PlayerChangeLook;

public final class TFM_setPlayerLook extends VarArgFunction {
    private final Room room;

    public TFM_setPlayerLook(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerLook() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setPlayerLook : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                String look = args.isnil(2) ? "1;0,0,0,0,0,0,0,0,0,0,0,0" : args.tojstring(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).tmpMouseLook = look;
                    this.room.sendAll(new C_PlayerChangeLook(this.room.getPlayers().get(playerName).getSessionId(), look));
                }
            }
        }
        return NIL;
    }
}