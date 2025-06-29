package org.transformice.packets.recv.player;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.player.C_PlayerAction;
import org.transformice.packets.send.transformice.C_RockPaperScissorsAction;

@SuppressWarnings("unused")
public final class S_PlayerAction implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        int actionType = data.readByte();
        int playerSessionId = data.readInt();
        String flag = "";
        if(data.getLength() > 0) {
            flag = data.readString();
        }

        switch (actionType) {
            case 10: // flag
                client.getRoom().sendAll(new C_PlayerAction(client.getSessionId(), 10, flag.isEmpty() ? client.getCountryLangue() : flag, false));
                break;
            case 14: // high five
            case 18: // hug
            case 22: // kissing
            case 26: // rock paper scissors.
                Client repPlayer = client.getServer().getPlayerBySessionId(playerSessionId);
                int num1 = SrcRandom.RandomNumber(0, 2);
                if(repPlayer != null && repPlayer.getRoom() == client.getRoom()) {
                    client.getRoom().sendAll(new C_PlayerAction(client.getSessionId(), actionType + 1, "", false));
                    repPlayer.getRoom().sendAll(new C_PlayerAction(repPlayer.getSessionId(), actionType + 1, "", false));
                    if(actionType == 26) {
                        int num2 = SrcRandom.RandomNumber(0, 2);
                        client.getRoom().sendAll(new C_RockPaperScissorsAction(client.getSessionId(), num1, repPlayer.getSessionId(), num2));
                    }
                }
                break;
            default:
                client.getRoom().sendAllOthers(client, new C_PlayerAction(client.getSessionId(), actionType, "", false));
                break;
        }

        if(client.isShaman) {
            client.getParseSkillsInstance().handleSkillAction(actionType);
        }

        if (client.getRoom().luaMinigame != null) {
            client.getRoom().luaApi.callEvent("eventEmotePlayed", client.getPlayerName(), actionType, flag);
        }
    }

    @Override
    public int getC() {
        return 8;
    }

    @Override
    public int getCC() {
        return 1;
    }

    @Override
    public boolean isLegacyPacket() {
        return false;
    }
}