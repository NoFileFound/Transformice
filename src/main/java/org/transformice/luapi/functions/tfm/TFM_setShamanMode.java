package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.player.C_SetShaman;

public final class TFM_setShamanMode extends VarArgFunction {
    private final Room room;

    public TFM_setShamanMode(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setShamanMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setShamanMode : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                if(this.room.getPlayers().get(playerName) != null) {
                    int mode = args.isnil(2) ? (this.room.getPlayers().get(playerName).getAccount().getShamanType()) : args.toint(2);
                    this.room.sendAll(new C_SetShaman(this.room.getPlayers().get(playerName).getSessionId(), mode, this.room.getPlayers().get(playerName).getAccount().getShamanLevel(), this.room.getPlayers().get(playerName).getAccount().getEquippedShamanBadge(), this.room.getLastObjectID() + 10000, this.room.getPlayers().get(playerName).getAccount().isShamanNoSkills()));
                }
            }
        }
        return NIL;
    }
}