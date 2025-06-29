package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_PlaySound;

public final class TFM_playSound extends VarArgFunction {
    private final Room room;

    public TFM_playSound(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.playSound() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.playSound : argument 1 can't be NIL."));
            } else {
                String soundFile = args.tojstring(1);
                int volume = (args.isnil(2) ? 70 : args.toint(2));
                int soundPosX = args.toint(3);
                int soundPosY = args.toint(4);
                String playerName = args.tojstring(5);
                if(this.room.getPlayers().get(playerName) != null) {
                    this.room.getPlayers().get(playerName).sendPacket(new C_PlaySound(soundFile, volume, soundPosX, soundPosY));
                } else {
                    this.room.sendAll(new C_PlaySound(soundFile, volume, soundPosX, soundPosY));
                }
            }
        }
        return NIL;
    }
}