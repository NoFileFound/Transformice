package org.transformice.luapi.functions.tfm;

// Imports
import org.bytearray.ByteArray;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.transformice.Room;
import org.transformice.libraries.Timer;
import org.transformice.packets.SendPacket;
import org.transformice.packets.send.lua.C_LuaMessage;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TFM_addConjuration extends VarArgFunction {
    private final Room room;

    public TFM_addConjuration(Room room) {
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
            this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addConjuration : argument 1 can't be NIL."));
        } else if (args.isnil(2)) {
            this.room.luaAdmin.sendPacket(new C_LuaMessage("tfm.exec.addConjuration : argument 2 can't be NIL."));
        } else {
            int xPosition = args.toint(1);
            int yPosition = args.toint(2);
            int duration = args.isnil(3) ? 10000 : args.toint(3);

            this.room.sendAllOld(new SendPacket() {
                @Override
                public int getC() {
                    return 4;
                }

                @Override
                public int getCC() {
                    return 14;
                }

                @Override
                public byte[] getPacket() {
                    return new ByteArray().writeByte(xPosition).writeByte(1).writeByte(yPosition).toByteArray();
                }
            });

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    room.sendAllOld(new SendPacket() {
                        @Override
                        public int getC() {
                            return 4;
                        }

                        @Override
                        public int getCC() {
                            return 15;
                        }

                        @Override
                        public byte[] getPacket() {
                            return new ByteArray().writeByte(xPosition).writeByte(1).writeByte(yPosition).toByteArray();
                        }
                    });
                }
            }, duration, TimeUnit.SECONDS);
        }
        return NIL;
    }
}