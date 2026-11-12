package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.room.PacketSendPlayerChangeSize;

public final class LuaChangePlayerSize extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.changePlayerSize() function.
     * @param room The given room.
     */
    public LuaChangePlayerSize(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.changePlayerSize() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.changePlayerSize : argument 1 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            int size = args.toint(2);
            if (size < 0.1 || size > 5) {
                this.room.getParseLua().sendLuaMessage("tfm.exec.changePlayerSize : The size must be between 0.1 and 5.");
            } else {
                if (this.room.getPlayers().get(playerName) != null) {
                    this.room.broadcastPacket(new PacketSendPlayerChangeSize(this.room.getPlayers().get(playerName).getSessionId(), size, false));
                }
            }
        }

        return NIL;
    }
}