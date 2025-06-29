package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_DisableProperties;

public final class TFM_disableMinimalistMode extends VarArgFunction {
    private final Room room;

    public TFM_disableMinimalistMode(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.disableMinimalistMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            this.room.disableMinimalistMode = args.isnil(1) || args.toboolean(1);
            this.room.sendAll(new C_DisableProperties(this.room.disableWatchCommand, this.room.disableDebugCommand, this.room.disableMinimalistMode));
        }
        return NIL;
    }
}