package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.newpackets.C_SetGravityScale;

public final class TFM_setPlayerGravityScale extends VarArgFunction {
    private final Room room;

    public TFM_setPlayerGravityScale(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setPlayerGravityScale() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            String targetPlayer = args.tojstring(1);
            int gravityScale = args.isnil(2) ? 1 : args.toint(2);
            int windScale = args.isnil(3) ? 1 : args.toint(3);
            if(targetPlayer.equals("nil")) {
                this.room.sendAll(new C_SetGravityScale(-1, gravityScale, windScale));
            } else {
                if(this.room.getPlayers().get(targetPlayer) != null) {
                    this.room.getPlayers().get(targetPlayer).sendPacket(new C_SetGravityScale(-1, gravityScale, windScale));
                }
            }
        }
        return NIL;
    }
}