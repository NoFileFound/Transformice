package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.informations.C_LogMessage;

public final class UI_addLogWindow extends VarArgFunction {
    private final Room room;

    public UI_addLogWindow(Room room) {
        this.room = room;
    }

    /**
     * Invokes the ui.addLogWindow() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (this.room.luaDebugLib != null && !this.room.luaDebugLib.checkTestCode()) {
            int fontId = args.isnil(1) ? 1 : args.toint(1);
            String text = args.tojstring(2);
            String targetPlayer = args.tojstring(3);
            if(targetPlayer.equals("nil")) {
                this.room.sendAll(new C_LogMessage(fontId, text));
            } else {
                if(this.room.getPlayers().get(targetPlayer) != null) {
                    this.room.getPlayers().get(targetPlayer).sendPacket(new C_LogMessage(fontId, text));
                }
            }
        }
        return NIL;
    }
}