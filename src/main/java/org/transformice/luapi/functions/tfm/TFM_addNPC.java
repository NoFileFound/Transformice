package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.player.C_CreateNewNPC;

public final class TFM_addNPC extends VarArgFunction {
    private final Room room;

    public TFM_addNPC(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addNPC() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addNPC : argument 1 can't be NIL."));
            } else if (!args.istable(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addNPC : argument 2 must be Table."));
            } else {
                String npcName = args.tojstring(1);
                LuaTable npcTable = args.checktable(2);
                String targetPlayer = args.tojstring(3);
                if(isnil(3)) {
                    this.room.sendAll(new C_CreateNewNPC(this.room.luaAdmin.getSessionId(), npcName, npcTable.get("title").toint(), npcTable.get("feminine").toboolean(), npcTable.get("look").tojstring(), npcTable.get("x").toint(), npcTable.get("y").toint(), npcTable.get("emote").toint(), npcTable.get("lookLeft").toboolean(), npcTable.get("lookAtPlayer").toboolean(), npcTable.get("interactive").toint(), npcTable.get("npcMessage").tojstring()));
                } else {
                    if(this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).sendPacket(new C_CreateNewNPC(this.room.luaAdmin.getSessionId(), npcName, npcTable.get("title").toint(), npcTable.get("feminine").toboolean(), npcTable.get("look").tojstring(), npcTable.get("x").toint(), npcTable.get("y").toint(), npcTable.get("emote").toint(), npcTable.get("lookLeft").toboolean(), npcTable.get("lookAtPlayer").toboolean(), npcTable.get("interactive").toint(), npcTable.get("npcMessage").tojstring()));
                    }
                }
            }
        }
        return NIL;
    }
}