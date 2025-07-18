package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;
import org.transformice.database.DBUtils;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;

public final class TFM_newGame extends VarArgFunction {
    private final Room room;

    public TFM_newGame(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.newGame() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if(args.isnil(1)) {
            this.room.changeMap();
            return NIL;
        }

        String mapCode = args.tojstring(1);
        if(mapCode.startsWith("@")) {
            if(DBUtils.findMapByCode(Integer.parseInt(mapCode.substring(1))) == null) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.newGame : The map is not found in the database."));
                return NIL;
            }

            this.room.forceNextMap = mapCode.substring(1);
        }

        else if(mapCode.startsWith("#")) {
            var info = DBUtils.findMapByCategory(Integer.parseInt(mapCode.substring(1)));
            if(info != null) {
                this.room.forceNextMap = "@" + info.getMapCode();
            }
        }

        else if(mapCode.startsWith("<")) {
            this.room.forceMapXml = mapCode;
        }

        this.room.changeMap();
        return NIL;
    }
}