package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_DespawnNPC;

public final class TFM_removeNPC extends VarArgFunction {
    private final Room room;

    public TFM_removeNPC(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removeNPC() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.removeNPC : argument 1 can't be NIL."));
            } else {
                String npcId = args.tojstring(1);
                String playerName = args.tojstring(2);
                if(playerName.equals("nil")) {
                    this.room.sendAll(new C_DespawnNPC(npcId));
                } else {
                    if(this.room.getPlayers().get(playerName) != null) {
                        this.room.getPlayers().get(playerName).sendPacket(new C_DespawnNPC(npcId));
                    }
                }
            }
        }
        return NIL;
    }
}