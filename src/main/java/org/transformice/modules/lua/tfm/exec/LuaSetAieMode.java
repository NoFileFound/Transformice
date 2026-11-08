package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.room.PacketSendAieMode;

public final class LuaSetAieMode extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.setAieMode() function.
     * @param room The given room.
     */
    public LuaSetAieMode(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.setAieMode() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        boolean enable = args.isnil(1) || args.toboolean(1);
        int sensibility = args.isnil(2) ? 1 : args.toint(2);
        if (args.isnil(3)) {
            this.room.broadcastPacket(new PacketSendAieMode(enable, sensibility));
        } else {
            String playerName = args.tojstring(3);
            if (this.room.getPlayers().get(playerName) != null)
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendAieMode(enable, sensibility));
        }

        return NIL;
    }
}