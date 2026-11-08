package org.transformice.modules.lua.system;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaBindKeyboard;

public final class LuaBindKeyboard extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new function of system.bindKeyboard().
     * @param room The room.
     */
    public LuaBindKeyboard(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the system.bindKeyboard() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("system.bindKeyboard : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("system.bindKeyboard : argument 2 can't be NIL.");
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("system.bindKeyboard : argument 3 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            int keyCode = args.toint(2);
            boolean down = args.toboolean(3);
            boolean activate = args.isnil(4) || args.toboolean(4);
            if (this.room.getPlayers().get(playerName) != null)
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendLuaBindKeyboard(keyCode, down, activate));
        }

        return LuaValue.NIL;
    }
}