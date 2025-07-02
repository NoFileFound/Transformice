package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Client;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_PlayerDemoteShaman;
import org.transformice.packets.send.player.C_SetShaman;

public final class TFM_setShaman extends VarArgFunction {
    private final Room room;

    public TFM_setShaman(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setShaman() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.setShaman : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                boolean remove = args.toboolean(2);
                if (this.room.getPlayers().containsKey(playerName)) {
                    if(remove) {
                        this.room.sendAll(new C_PlayerDemoteShaman(this.room.getPlayers().get(playerName).getSessionId()));
                    } else {
                        Client player = this.room.getPlayers().get(playerName);
                        this.room.sendAll(new C_SetShaman(player.getSessionId(), player.getAccount().getShamanType(), player.getAccount().getShamanLevel(), player.getParseSkillsInstance().getShamanBadge(), this.room.getLastObjectID() + 10000, player.getAccount().isShamanNoSkills()));
                    }
                }
            }
        }
        return NIL;
    }
}