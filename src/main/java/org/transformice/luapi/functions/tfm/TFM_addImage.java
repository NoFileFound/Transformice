package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_addImage extends VarArgFunction {
    private final Room room;

    public TFM_addImage(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addImage() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addImage : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addImage : argument 2 can't be NIL."));
            } else {
                String imageName = args.tojstring(1);
                if(imageName.matches(".*(?:\\.{2,}|^/|/\\.\\.|\\.\\./).*") || imageName.length() > 24 || !imageName.endsWith(".png")) {
                    this.room.luaAdmin.sendPacket(new C_LuaMessage(String.format("tfm.exec.addImage : Forbidden image, use http://atelier801.com/images?pr=%s. With this image : http://images.atelier801.com/149a49e4b38.png, use 149a49e4b38.png as image parameter.", this.room.luaAdmin.getPlayerName())));
                    return NIL;
                }

                String target = args.tojstring(2);
                int xPosition = args.toint(3);
                int yPosition = args.toint(4);
                String targetPlayer = args.tojstring(5);
                int xScale = args.toint(6);
                int yScale = args.toint(7);
                int rotation = args.toint(8);
                int alpha = args.toint(9);
                int anchorX = args.toint(10);
                int anchorY = args.toint(11);
                boolean fadeIn = args.toboolean(12);
                this.room.addImage(imageName, target, xPosition, yPosition, xScale, yScale, rotation, alpha, anchorX, anchorY, fadeIn, targetPlayer);
            }
        }
        return NIL;
    }
}