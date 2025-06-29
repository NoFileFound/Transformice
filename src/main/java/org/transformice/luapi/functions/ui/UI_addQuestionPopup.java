package org.transformice.luapi.functions.ui;

// Imports
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;

// Packets
import org.transformice.packets.send.lua.C_LuaMessage;
import org.transformice.packets.send.transformice.C_QuestionPopup;

public final class UI_addQuestionPopup extends VarArgFunction {
    private final Room room;

    public UI_addQuestionPopup(Room room) {
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
            if (args.isnil(1)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.addQuestionPopup : argument 1 can't be NIL."));
            } else if (args.isnil(2)) {
                this.room.luaAdmin.sendPacket(new C_LuaMessage("ui.addQuestionPopup : argument 2 can't be NIL."));
            } else {
                String question = args.tojstring(1);
                int badgeId = args.toint(2);
                boolean isShamanItem = args.toboolean(3);
                String targetPlayer = args.tojstring(4);
                boolean arg1 = args.toboolean(5);
                String arg2 = args.tojstring(6);
                if(targetPlayer.equals("nil")) {
                    this.room.sendAll(new C_QuestionPopup(question, badgeId, isShamanItem, arg1, arg2));
                } else {
                    if(this.room.getPlayers().get(targetPlayer) != null) {
                        this.room.getPlayers().get(targetPlayer).sendPacket(new C_QuestionPopup(question, badgeId, isShamanItem, arg1, arg2));
                    }
                }
            }
        }
        return NIL;
    }
}