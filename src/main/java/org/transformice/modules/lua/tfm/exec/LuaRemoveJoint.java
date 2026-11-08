package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaRemoveJoint;

public final class LuaRemoveJoint extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.removeJoint() function.
     * @param room The given room.
     */
    public LuaRemoveJoint(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removeJoint() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.removeJoint : argument 1 can't be NIL.");
        } else {
            int jointId = args.toint(1);
            this.room.broadcastPacket(new PacketSendLuaRemoveJoint(jointId));
        }

        return NIL;
    }
}