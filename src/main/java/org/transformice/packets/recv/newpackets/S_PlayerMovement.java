package org.transformice.packets.recv.newpackets;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.libraries.Pair;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.newpackets.C_PlayerMovement;

@SuppressWarnings("unused")
public final class S_PlayerMovement implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        data.decryptPacket(Application.getSwfInfo().packet_keys, fingerPrint);

        int roundCode = data.readInt128();
        if(roundCode == client.getRoom().getLastRoundId()) {
            client.isFacingRight = data.readBoolean();
            client.isFacingLeft = data.readBoolean();
            var position = new Pair<>((int)(data.readInt128() * (30.0 / 100)), (int)(data.readInt128()* (30.0 / 100)));
            if (!position.equals(client.getPosition())) {
                client.setPosition(position);
                if (client.isAfk) {
                    client.isAfk = false;
                }
            }

            client.setVelocity(new Pair<>((int)(data.readInt128() * (30.0 / 100)), (int)(data.readInt128()* (30.0 / 100))));

            var frictionVar1 = data.readInt128() * (30.0 / 100);
            var frictionVar2 = data.readInt128() * (30.0 / 100);
            client.isJumping = data.readBoolean();

            client.getRoom().sendAllOthers(client, new C_PlayerMovement(client.getSessionId(), new ByteArray().writeBoolean(client.isFacingRight).writeBoolean(client.isFacingLeft).writeInt128((int)(client.getPosition().getFirst() / 0.3)).writeInt128((int)(client.getPosition().getSecond() / 0.3)).writeInt128((int)(client.getVelocity().getFirst() / 0.3)).writeInt128((int)(client.getVelocity().getSecond() / 0.3)).writeInt128((int)(frictionVar1 / 0.3)).writeInt128((int)(frictionVar2 / 0.3)).writeBoolean(client.isJumping).writeBytes(data.toByteArray())));
            if (client.getRoom().luaMinigame != null) {
                client.getRoom().updatePlayerList(client);
            }
        }
    }

    @Override
    public int getC() {
        return 149;
    }

    @Override
    public int getCC() {
        return 26;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}