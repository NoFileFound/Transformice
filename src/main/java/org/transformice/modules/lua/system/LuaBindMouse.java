package org.transformice.modules.lua.system;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaBindMouseClick;

public final class LuaBindMouse extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new function of system.bindMouse().
     * @param room The room.
     */
    public LuaBindMouse(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the system.bindMouse() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("system.bindMouse : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            boolean active = args.isnil(2) || args.toboolean(2);
            if(this.room.getPlayers().get(playerName) != null) {
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendLuaBindMouseClick(active));
            }
        }

        return LuaValue.NIL;
    }
}