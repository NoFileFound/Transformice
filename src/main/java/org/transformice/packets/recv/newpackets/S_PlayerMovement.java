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
            var position = new Pair<>((int)(data.readInt128() * (30.0 / 100)), (int)(data.readInt128() * (30.0 / 100)));
            client.setVelocity(new Pair<>((int)(data.readInt128() * (1.0 / 10)), (int)(data.readInt128() * (1.0 / 10))));
            if(client.getPosition().getFirst() == -1 && client.getPosition().getSecond() == -1) {
                client.setPosition(new Pair<>((int)((position.getFirst() + client.getVelocity().getFirst() * (client.lastPingResponse / 2000.0)) * (100.0 / 30)), (int)((position.getSecond() + client.getVelocity().getSecond() * (client.lastPingResponse / 2000.0)) * (100.0 / 30))));
                client.setVelocity(new Pair<>(client.getVelocity().getFirst() * 10, client.getVelocity().getSecond() * 10));
            } else if(!client.getPosition().equals(position)) {
                client.setPosition(new Pair<>((int)((position.getFirst() + client.getVelocity().getFirst() * (client.lastPingResponse / 2000.0)) * (100.0 / 30)), (int)((position.getSecond() + client.getVelocity().getSecond() * (client.lastPingResponse / 2000.0)) * (100.0 / 30))));
                client.setVelocity(new Pair<>(client.getVelocity().getFirst() * 10, client.getVelocity().getSecond() * 10));
                if(client.isAfk) {
                    client.isAfk = false;
                }
            }

            var frictionVar1 = data.readInt128() / 100;
            var frictionVar2 = data.readInt128() / 10;
            client.isJumping = data.readBoolean();

            client.getRoom().sendAllOthers(client, new C_PlayerMovement(client.getSessionId(), new ByteArray().writeBoolean(client.isFacingRight).writeBoolean(client.isFacingLeft).writeInt128(client.getPosition().getFirst()).writeInt128(client.getPosition().getSecond()).writeInt128(client.getVelocity().getFirst()).writeInt128(client.getVelocity().getSecond()).writeInt128(frictionVar1).writeInt128(frictionVar2).writeBoolean(client.isJumping).writeBytes(data.toByteArray())));
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