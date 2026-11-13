package org.transformice.modules.lua.tfm.exec;

// Imports
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

public final class LuaAddImage extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.addImage() function.
     * @param room The given room.
     */
    public LuaAddImage(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addImage() function.
     * @param args The arguments.
     * @return The last image id.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addImage : argument 1 can't be NIL.");
            return NIL;
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addImage : argument 2 can't be NIL.");
            return NIL;
        } else {
            String imageName = args.tojstring(1);
            if (imageName.length() > 24) {
                this.room.getParseLua().sendLuaMessage(String.format("tfm.exec.addImage : Forbidden image, use http://atelier801.com/module-images?pr=%s. With this image : http://images.atelier801.com/149a49e4b38.png, use 149a49e4b38.png as image parameter.", this.room.getParseLua().getLuaAdminName()));
                return NIL;
            }

            String target = args.tojstring(2);
            int xPosition = args.toint(3);
            int yPosition = args.toint(4);
            String targetPlayer = args.tojstring(5);
            int xScale = args.isnil(6) ? 1 : args.toint(6);
            int yScale = args.isnil(7) ? 1 : args.toint(7);
            int rotation = args.toint(8);
            int alpha = args.isnil(9) ? 1 : args.toint(9);
            int anchorX = args.toint(10);
            int anchorY = args.toint(11);
            boolean fadeIn = args.toboolean(12);
            this.room.addImage(imageName, target, xPosition, yPosition, xScale, yScale, rotation, alpha, anchorX, anchorY, fadeIn, targetPlayer);
            return LuaValue.valueOf(this.room.getLastImageId());
        }
    }
}