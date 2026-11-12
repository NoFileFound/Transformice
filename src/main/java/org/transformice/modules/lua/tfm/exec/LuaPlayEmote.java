package org.transformice.modules.lua.tfm.exec;

// Imports
import java.util.regex.Pattern;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.GameRoom;

// Packets
import org.transformice.packets.send.player.PacketSendPlayerAction;

public final class LuaPlayEmote extends VarArgFunction {
    private static final Pattern INVALID_EMOTE_PATTERN = Pattern.compile(".*(?:\\.{2,}|^/|/\\.\\.|\\.\\./).*");
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.playEmote() function.
     * @param room The given room.
     */
    public LuaPlayEmote(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.playEmote() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.playEmote : argument 1 can't be NIL.");
        } else if (args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.playEmote : argument 2 can't be NIL.");
        } else {
            String playerName = args.tojstring(1);
            int emoteId = args.toint(2);
            String emoteArg = args.tojstring(3);
            if (INVALID_EMOTE_PATTERN.matcher(emoteArg).matches())
                return NIL;

            if (this.room.getPlayers().get(playerName) != null)
                this.room.broadcastPacket(new PacketSendPlayerAction(this.room.getPlayers().get(playerName).getSessionId(), emoteId, emoteArg, true));
        }

        return NIL;
    }
}