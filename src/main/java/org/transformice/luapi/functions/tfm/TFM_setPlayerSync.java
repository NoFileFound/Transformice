package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Client;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.legacy.player.C_PlayerSync;
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_setPlayerSync extends VarArgFunction {
    private final Room room;

    public TFM_setPlayerSync(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerSync() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setPlayerSync : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                if (this.room.getPlayers().containsKey(playerName)) {
                    this.room.setCurrentSync(this.room.getPlayers().get(playerName));
                    for(Client player : this.room.getPlayers().values()) {
                        player.sendPacket(new C_PlayerSync(this.room.getPlayers().get(playerName).getSessionId(), false));
                    }
                }
            }
        }
        return NIL;
    }
}