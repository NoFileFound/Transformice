package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaRemovePhysicObject;

public final class LuaRemovePhysicObject extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.removePhysicObject() function.
     * @param room The given room.
     */
    public LuaRemovePhysicObject(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removePhysicObject() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.removePhysicObject : argument 1 can't be NIL.");
        } else {
            int objectId = args.toint(1);
            this.room.broadcastPacket(new PacketSendLuaRemovePhysicObject(objectId));
        }

        return NIL;
    }
}