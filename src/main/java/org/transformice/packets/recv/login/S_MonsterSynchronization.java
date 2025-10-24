package org.transformice.packets.recv.login;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.login.C_SetMonsterSpeed;
import org.transformice.packets.send.room.C_SummonEventElement;

@SuppressWarnings("unused")
public final class S_MonsterSynchronization implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int monsters = data.readShort();
        for(int i = 0; i < monsters; i++)
        {
            int monsterId = data.readInt();
            int x = data.readInt();
            int y = data.readInt();
            long currentTime = System.currentTimeMillis();
            int direction = (client.isFacingLeft) ? 1 : -1;
            if (currentTime - client.getRoom().getMonsterLastChange().get(monsterId) >= 5000) {
                client.getRoom().getMonsterLastChange().put(monsterId, currentTime);
                 if(monsterId == 0) {
                    client.sendPacket(new C_SummonEventElement(10));
                } else {
                    client.sendPacket(new C_SetMonsterSpeed(monsterId, direction * 3));
                }
            }
        }
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 10;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}