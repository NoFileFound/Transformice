package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.player.C_MovePlayer;

public final class TFM_movePlayer extends VarArgFunction {
    private final Room room;

    public TFM_movePlayer(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.movePlayer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.movePlayer : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.movePlayer : argument 2 can't be NIL."));
            } else if (args.isnil(3)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.movePlayer : argument 3 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                int xPosition = args.toint(2);
                int yPosition = args.toint(3);
                boolean positionOffset = args.toboolean(4);
                int xSpeed = args.toint(5);
                int ySpeed = args.toint(6);
                boolean speedOffset = args.toboolean(7);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).sendPacket(new C_MovePlayer(xPosition, yPosition, positionOffset, xSpeed, ySpeed, speedOffset));
                }
            }
        }
        return NIL;
    }
}