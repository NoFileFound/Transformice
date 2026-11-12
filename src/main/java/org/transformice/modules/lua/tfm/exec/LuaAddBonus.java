package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;
import org.transformice.data.objects.AddBonusType;

// Packets
import org.transformice.packets.send.room.PacketSendAddBonus;

public final class LuaAddBonus extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.addBonus() function.
     * @param room The given room.
     */
    public LuaAddBonus(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addBonus() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addBonus : argument 1 can't be NIL.");
        } else {
            int bonusType = args.toint(1);
            int x = args.toint(2);
            int y = args.toint(3);
            int id = args.toint(4);
            int angle = args.toint(5);
            boolean visible = args.toboolean(6);
            String playerName = args.tojstring(7);
            if(args.isnil(7)) {
                this.room.broadcastPacket(new PacketSendAddBonus(AddBonusType.parseFrom(bonusType), x, y, angle, id, visible));
            } else {
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendAddBonus(AddBonusType.parseFrom(bonusType), x, y, angle, id, visible));
                }
            }
        }

        return NIL;
    }
}