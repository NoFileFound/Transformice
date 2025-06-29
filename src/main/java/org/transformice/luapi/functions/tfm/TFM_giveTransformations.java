package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.transformation.C_EnableTransformation;

public final class TFM_giveTransformations extends VarArgFunction {
    private final Room room;

    public TFM_giveTransformations(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.giveTransformations() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.giveTransformations : argument 1 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                boolean canTransform = args.toboolean(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).canTransform = canTransform;
                    this.room.getPlayers().get(playerName).sendPacket(new C_EnableTransformation(canTransform));
                }
            }
        }
        return NIL;
    }
}