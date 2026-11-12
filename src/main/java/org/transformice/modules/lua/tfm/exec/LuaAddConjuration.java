package org.transformice.modules.lua.tfm.exec;

// Imports
import java.util.concurrent.TimeUnit;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.psframework.utils.PSTimer;
import org.transformice.GameRoom;
import org.transformice.network.NetByteArray;
import org.transformice.packets.LegacyPacketOpcodes;

public final class LuaAddConjuration extends VarArgFunction {
    private final GameRoom room;

    /**
     * Creates a new tfm.exec.addConjuration() function.
     * @param room The given room.
     */
    public LuaAddConjuration(GameRoom room) {
        this.room = room;
    }

    /**
     * Invokes the tfm.exec.addConjuration() function.
     * @param args The arguments.
     * @return NIL.
     */
    @Override
    public Varargs invoke(Varargs args) {
        if (args.isnil(1)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addConjuration : argument 1 can't be NIL.");
        } else if(args.isnil(2)) {
            this.room.getParseLua().sendLuaMessage("tfm.exec.addConjuration : argument 2 can't be NIL.");
        } else {
            int posX = args.toint(1);
            int posY = args.toint(2);
            int duration = (args.isnil(3) ? 10000 : args.toint(3));
            new PSTimer(this.room.getServer().getScheduler()).schedule(() -> this.room.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapDestroyConjuration) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    NetByteArray packet = new NetByteArray();
                    packet.writeUTF(String.valueOf(posX), false);
                    packet.writeByte(1);
                    packet.writeUTF(String.valueOf(posY), false);
                    return packet;
                }
            }), duration, TimeUnit.MILLISECONDS);

            this.room.broadcastLegacyPacket(new PSSendPacket(LegacyPacketOpcodes.SendMapAddConjuration) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    NetByteArray packet = new NetByteArray();
                    packet.writeUTF(String.valueOf(posX), false);
                    packet.writeByte(1);
                    packet.writeUTF(String.valueOf(posY), false);
                    return packet;
                }
            });
        }

        return NIL;
    }
}