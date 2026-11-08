package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaPlaySound;

public final class LuaPlaySound extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.playSound() function.
     * @param room The given room.
     */
    public LuaPlaySound(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.playSound() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.playSound : argument 1 can't be NIL.");
        } else {
            String soundFile = args.tojstring(1);
            int volume = (args.isnil(2) ? 70 : args.toint(2));
            int soundPosX = args.toint(3);
            int soundPosY = args.toint(4);
            String playerName = args.tojstring(5);
            if(this.room.getPlayers().get(playerName) != null) {
                this.room.getPlayers().get(playerName).broadcastPacket(new PacketSendLuaPlaySound(soundFile, volume, soundPosX, soundPosY));
            } else {
                this.room.broadcastPacket(new PacketSendLuaPlaySound(soundFile, volume, soundPosX, soundPosY));
            }
        }

        return NIL;
    }
}