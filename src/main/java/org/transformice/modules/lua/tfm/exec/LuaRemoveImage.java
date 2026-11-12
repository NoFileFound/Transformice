package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.lua.PacketSendLuaRemoveImage;

public final class LuaRemoveImage extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.removeImage() function.
     * @param room The given room.
     */
    public LuaRemoveImage(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.removeImage() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.removeImage : argument 1 can't be NIL.");
        } else {
            int imageId = args.toint(1);
            boolean fadeOut = !args.isnil(2) && args.toboolean(2);
            this.room.broadcastPacket(new PacketSendLuaRemoveImage(imageId, fadeOut));
        }

        return NIL;
    }
}