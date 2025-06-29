package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.player.C_PlayerAction;

public final class TFM_playEmote extends VarArgFunction {
    private final Room room;

    public TFM_playEmote(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.playEmote() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.playEmote : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.playEmote : argument 2 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                int emoteId = args.toint(2);
                String emoteArg = args.tojstring(3);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.sendAll(new C_PlayerAction(this.room.getPlayers().get(playerName).getSessionId(), emoteId, emoteArg, true));
                }
            }
        }
        return NIL;
    }
}