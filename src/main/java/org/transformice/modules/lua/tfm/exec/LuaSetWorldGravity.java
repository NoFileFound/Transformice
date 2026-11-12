package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.room.PacketSendSetRoomGravity;

public final class LuaSetWorldGravity extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setWorldGravity() function.
     * @param room The given room.
     */
    public LuaSetWorldGravity(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setWorldGravity() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        int gravity = args.isnil(1) ? 0 : args.toint(1);
        int wind = args.isnil(2) ? 0 : args.toint(2);
        this.room.broadcastPacket(new PacketSendSetRoomGravity(gravity, wind, 0));
        return NIL;
    }
}