package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerAttachToBalloon;
import org.transformice.packets.send.player.PacketSendPlayerDetachFromBalloon;

public final class LuaAttachBalloon extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.attachBalloon() function.
     * @param room The given room.
     */
    public LuaAttachBalloon(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.attachBalloon() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.attachBalloon : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.attachBalloon : argument 2 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            boolean attach = args.isnil(2) || args.toboolean(2);
            int color = args.isnil(3) ? 1 : args.toint(3);
            boolean transparent = args.isnil(4) || args.toboolean(4);
            int speed = args.isnil(5) ? 1 : args.toint(5);
            if (this.room.getPlayers().get(playerName) != null) {
                if (attach) {
                    this.room.sendAddShamanObject(this.room.getNextObjectId(), 28, this.room.getPlayers().get(playerName).positionX, this.room.getPlayers().get(playerName).positionY - 25, 0, 0, 0, transparent, false, this.room.getPlayers().get(playerName), true);
                    this.room.broadcastPacket(new PacketSendPlayerAttachToBalloon(this.room.getPlayers().get(playerName).getSessionId(), color, speed));
                } else {
                    this.room.broadcastPacket(new PacketSendPlayerDetachFromBalloon(this.room.getPlayers().get(playerName).getSessionId()));
                }
            }
        }

        return NIL;
    }
}