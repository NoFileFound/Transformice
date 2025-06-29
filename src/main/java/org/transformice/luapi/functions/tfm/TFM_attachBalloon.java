package org.transformice.luapi.functions.tfm;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.newpackets.C_AttachPlayerToBalloon;
import org.transformice.packets.send.newpackets.C_DetachPlayerFromBalloon;

public final class TFM_attachBalloon extends VarArgFunction {
    private final Room room;

    public TFM_attachBalloon(Room room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.attachBalloon() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.attachBalloon : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.attachBalloon : argument 2 can't be NIL."));
            } else {
                String playerName = args.tojstring(1);
                boolean attach = args.isnil(2) || args.toboolean(2);
                int color = args.isnil(3) ? 1 : args.toint(3);
                boolean transparent = args.isnil(4) || args.toboolean(4);
                int speed = args.isnil(5) ? 1 : args.toint(5);

                if(this.room.getPlayers().get(playerName) != null) {
                    if(attach) {
                        this.room.sendPlaceObject(this.room.getLastObjectID() + 1, 28, this.room.getPlayers().get(playerName).getPosition().getFirst(), this.room.getPlayers().get(playerName).getPosition().getSecond() - 25, 0, 0, 0, transparent, false, new byte[]{}, null, true);
                        this.room.sendAll(new C_AttachPlayerToBalloon(this.room.getPlayers().get(playerName).getSessionId(), color, speed));
                    } else {
                        this.room.sendAll(new C_DetachPlayerFromBalloon(this.room.getPlayers().get(playerName).getSessionId()));
                    }
                }
            }
        }
        return NIL;
    }
}