package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.room.PacketSendDemolitionSkill;

public final class LuaRemoveBonus extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.removeBonus() function.
     * @param room The given room.
     */
    public LuaRemoveBonus(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removeBonus() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.removeBonus : argument 1 can't be NIL.");
        } else {
            int id = args.toint(1);
            if (args.isnil(2)) {
                this.room.broadcastPacket(new PacketSendDemolitionSkill(id));
            } else {
                String playerName = args.tojstring(2);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendDemolitionSkill(id));
                }
            }
        }

        return NIL;
    }
}