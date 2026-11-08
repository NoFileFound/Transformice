package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerMove;

public final class LuaMovePlayer extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.movePlayer() function.
     * @param room The given room.
     */
    public LuaMovePlayer(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.movePlayer() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.movePlayer : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.movePlayer : argument 2 can't be NIL.");
        } else if (args.isnil(3)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.movePlayer : argument 3 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            int xPosition = args.toint(2);
            int yPosition = args.toint(3);
            boolean positionOffset = args.toboolean(4);
            int xSpeed = args.toint(5);
            int ySpeed = args.toint(6);
            boolean speedOffset = args.toboolean(7);
            if (this.room.getPlayers().get(playerName) != null)
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendPlayerMove(xPosition, yPosition, positionOffset, xSpeed, ySpeed, speedOffset));
        }

        return NIL;
    }
}