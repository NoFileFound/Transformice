package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_giveConsumables extends VarArgFunction {
    private final Room room;

    public TFM_giveConsumables(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.giveConsumables() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.giveConsumables : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.giveConsumables : argument 2 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                String consumableId = args.tojstring(2);
                int amount = args.isnil(2) ? 1 : args.toint(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).getParseInventoryInstance().addConsumable(consumableId, amount, true);
                }
            }
        }
        return NIL;
    }
}