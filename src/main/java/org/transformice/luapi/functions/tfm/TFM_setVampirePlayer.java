package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.player.C_VampireMode;

public final class TFM_setVampirePlayer extends VarArgFunction {
    private final Room room;

    public TFM_setVampirePlayer(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setVampirePlayer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setVampirePlayer : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                boolean isVampire = args.isnil(2) || args.toboolean(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).isVampire = isVampire;
                    this.room.sendAll(new C_VampireMode(this.room.getPlayers().get(playerName).getSessionId(), isVampire, false));
                }
            }
        }
        return NIL;
    }
}