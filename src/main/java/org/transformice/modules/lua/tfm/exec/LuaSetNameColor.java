package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaNicknameColor;

public final class LuaSetNameColor extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setNameColor() function.
     * @param room The given room.
     */
    public LuaSetNameColor(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setNameColor() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.setNameColor : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.setNameColor : argument 2 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            int color = args.toint(2);
            if (this.room.getPlayers().get(playerName) != null)
                this.room.broadcastPacket(new PacketSendLuaNicknameColor(this.room.getPlayers().get(playerName).getSessionId(), color));
        }

        return NIL;
    }
}