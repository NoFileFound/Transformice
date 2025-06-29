package org.transformice.packets.recv.chat;

// Imports
import org.bytearray.ByteArray;
import org.transformice.Client;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.chat.C_StaffChannelMessage;

@SuppressWarnings("unused")
public final class S_StaffChatMessage implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        byte channelId = data.readByte();
        String channelMessage = data.readString();

        boolean perm;
        boolean isInternational = true;
        switch(channelId) {
            case 0: // /ms
                perm = client.hasStaffPermission("Modo", "StaffChannel");
                break;
            case 1: // /mss
                perm = client.hasStaffPermission("Modo", "StaffChannel");
                break;
            case 2: // #Arbitre
            case 3: // #Modo
                perm = client.hasStaffPermission("Modo", "StaffChannel");
                isInternational = false;
                break;
            case 4: // #Modo (ALL)
            case 5: // #Arbutre (ALL)
                perm = client.hasStaffPermission("Modo", "StaffChannel");
                break;
            case 6: // /mssc
                perm = client.hasStaffPermission("Admin", "StaffChannel");
                break;
            case 7: // #LuaTeam
                perm = client.hasStaffPermission("LuaDev", "StaffChannel");
                break;
            case 8: // #MapCrew
                perm = client.hasStaffPermission("MapCrew", "StaffChannel");
                break;
            case 9: // #FunCorp
                perm = client.hasStaffPermission("FunCorp", "StaffChannel");
                break;
            case 10: // #FashionSquad
                perm = client.hasStaffPermission("FashionSquad", "StaffChannel");
                break;
            default:
                perm = false;
                break;
        }

        if(perm) {
            if(channelId == 0) { // ms command
                for(Client player : client.getRoom().getPlayers().values()) {
                    player.sendPacket(new C_StaffChannelMessage(0, "", channelMessage));
                }
                return;
            }

            if(channelId == 1) { // mss command
                for(Client player : client.getServer().getPlayers().values()) {
                    player.sendPacket(new C_StaffChannelMessage(1, client.getPlayerName(), channelMessage));
                }
                return;
            }

            if(channelId == 6) { // mssc command
                for(Client player : client.getServer().getPlayers().values()) {
                    if(player.playerCommunity.equals(client.playerCommunity)) {
                        player.sendPacket(new C_StaffChannelMessage(6, client.getPlayerName(), channelMessage));
                    }
                }
                return;
            }

            client.getServer().sendStaffChannelMessage(channelId, channelMessage, isInternational, client.getPlayerName(), client.playerCommunity);
        }
    }

    @Override
    public int getC() {
        return 6;
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