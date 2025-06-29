package org.transformice.modules;

// Imports
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Account;
import org.transformice.database.collections.Sanction;
import org.transformice.database.collections.Tribe;
import org.transformice.database.embeds.TribeHistoricEntry;
import org.transformice.database.embeds.TribeRank;
import org.transformice.packets.TribullePacket;
import org.transformice.packets.identifiers.TribulleNew;
import org.transformice.utils.Langue;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.informations.C_TranslationMessage;

public final class ParseTribulle {
    private final Client client;
    private final Server server;

    /**
     * Creates a new tribulle for given player.
     * @param client The given player.
     */
    public ParseTribulle(final Client client) {
        this.client = client;
        this.server = client.getServer();
    }

    /**
     * Handles the given tribulle packet.
     * @param code The tribulle packet code.
     * @param tribulleId The tribulle id.
     * @param data The tribulle packet.
     */
    public void handleTribullePacket(short code, long tribulleId, ByteArray data) {
        switch (code) {
            case TribulleNew.Receive.ST_ChangerDeGenre:
                this.sendChangePlayerGender(tribulleId, data.readByte());
                break;
            case TribulleNew.Receive.ST_AjoutAmi:
                this.sendAddFriend(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_DemandeEnMariage:
                this.sendMarriageInvitation(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_RepondDemandeEnMariage:
                this.sendMarriageAnswer(tribulleId, data.readString(), data.readBoolean());
                break;
            case TribulleNew.Receive.ST_DemandeDivorce:
                this.sendMarriageDivorce(tribulleId);
                break;
            case TribulleNew.Receive.ST_RetireAmi:
                this.sendRemoveFriend(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_ListeAmis:
                this.sendFriendList(tribulleId, false);
                break;
            case TribulleNew.Receive.ST_FermeeListeAmis:
                this.sendCloseFriendList(tribulleId);
                break;
            case TribulleNew.Receive.ST_AjoutNoire:
                this.sendAddIgnored(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_RetireListeNoire:
                this.sendRemoveIgnored(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_ListeNoire:
                this.sendIgnoredList(tribulleId);
                break;
            case TribulleNew.Receive.ST_EnvoiMessageChat:
                this.sendGlobalChannelMessage(tribulleId, data.readString(), data.readString());
                break;
            case TribulleNew.Receive.ST_EnvoiMessageTribu:
                this.sendTribeMessage(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_EnvoiMessagePrive:
                this.sendPlayerWhisperMessage(tribulleId, data.readString(), data.readString());
                break;
            case TribulleNew.Receive.ST_RejoindreCanalPublique:
                this.sendCreateNewGlobalChannel(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_QuitterCanalPublique:
                this.sendCloseGlobalChannel(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_DemandeMembresCanal:
                this.sendGlobalChannelMembersList(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_DefinitModeSilence:
                this.sendDefineModeSilence(tribulleId, data.readByte(), data.readString());
                break;
            case TribulleNew.Receive.ST_InvitationTribu:
                this.sendTribeMemberInvitation(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_RepondsInvitationTribu:
                this.sendTribeMemberInvitationAnswer(tribulleId, data.readString(), data.readBoolean());
                break;
            case TribulleNew.Receive.ST_DemandeQuitterTribu:
                this.sendTribeMemberLeave(tribulleId);
                break;
            case TribulleNew.Receive.ST_DemandeCreerTribu:
                this.sendCreateNewTribe(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_ChangerMessageJour:
                this.sendChangeTribeMessage(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_ChangerCodeMaisonTFM:
                this.sendChangeTribeHouseMap(tribulleId, data.readInt());
                break;
            case TribulleNew.Receive.ST_ExclureMembre:
                this.sendTribeMemberKick(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_OuvertureInterfaceTribu:
                this.sendOpenTribe(tribulleId, data.readBoolean());
                break;
            case TribulleNew.Receive.ST_FermetureInterfaceTribu:
                this.sendCloseTribe(tribulleId);
                break;
            case TribulleNew.Receive.ST_ChangementDroitsRang:
                this.sendChangeTribeRankPermission(tribulleId, data.readByte(), data.readInt(), data.readBoolean());
                break;
            case TribulleNew.Receive.ST_AffecterRang:
                this.sendChangeTribeMemberRank(tribulleId, data.readString(), data.readByte());
                break;
            case TribulleNew.Receive.ST_ChangementNomRang:
                this.sendChangeTribeRankName(tribulleId, data.readByte(), data.readString());
                break;
            case TribulleNew.Receive.ST_AjoutRang:
                this.sendCreateTribeRank(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_SupprimeRang:
                this.sendDeleteTribeRank(tribulleId, data.readByte());
                break;
            case TribulleNew.Receive.ST_InverserRang:
                this.sendChangeTribeRankPosition(tribulleId, data.readByte(), data.readByte());
                break;
            case TribulleNew.Receive.ST_DesignerChef:
                this.sendChangeTribeLeader(tribulleId, data.readString());
                break;
            case TribulleNew.Receive.ST_DemandeDissoudreTribu:
                this.sendDissolveTribe(tribulleId);
                break;
            case TribulleNew.Receive.ST_HistoriqueTribu:
                this.sendTribeHistory(tribulleId, data.readInt(), data.readInt());
                break;
            default:
                Application.getLogger().warn(Application.getTranslationManager().get("unhandledtribullepacket", code));
        }
    }

    /**
     * Adds a friend in the friend list.
     * @param tribulleId The tribulle id.
     * @param friendName The friend name to add.
     */
    private void sendAddFriend(final long tribulleId, final String friendName) {
        if(friendName == null || friendName.equals(this.client.getPlayerName())) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutAmi, new ByteArray().writeInt(tribulleId).writeByte(15));
            return;
        }

        if(this.client.getAccount().getFriendList().contains(friendName)) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutAmi, new ByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        if(this.client.getAccount().getFriendList().size() > 250) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutAmi, new ByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        if(this.server.getPlayerAccount(friendName) == null) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutAmi, new ByteArray().writeInt(tribulleId).writeByte(12));
            return;
        }

        this.client.getAccount().getFriendList().add(friendName);
        ByteArray data = new ByteArray();
        data.writeBytes(this.buildFriendInfo(friendName, this.client.getPlayerName()).toByteArray());
        this.sendTribullePacket(TribulleNew.Send.ET_SignalementAjoutAmi, data);
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutAmi, new ByteArray().writeInt(tribulleId).writeByte(1));
        if(this.server.checkIsConnected(friendName)) {
            this.server.getPlayers().get(friendName).getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignalementModificationAmi, this.buildFriendInfo(this.client.getPlayerName(), friendName));
        }
    }

    /**
     * Adds a player in the block list.
     * @param tribulleId The tribulle id.
     * @param ignoredName The player's name to add.
     */
    private void sendAddIgnored(final long tribulleId, final String ignoredName) {
        if(ignoredName.equals(this.client.getPlayerName()) || ignoredName.equals(this.client.getAccount().getSoulmate()) || this.client.getAccount().getIgnoredList().contains(ignoredName)) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutListeNoire, new ByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        if(this.client.getAccount().getIgnoredList().size() > 250) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutListeNoire, new ByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        if(this.server.getPlayerAccount(ignoredName) == null) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutListeNoire, new ByteArray().writeInt(tribulleId).writeByte(12));
            return;
        }

        this.client.getAccount().getFriendList().remove(ignoredName);
        this.client.getAccount().getIgnoredList().add(ignoredName);
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutListeNoire, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the player's avatar id.
     * @param avatarId The avatar id.
     */
    public void sendChangePlayerAvatar(int avatarId) {
        this.client.getAccount().setAvatarId(avatarId);
        this.sendTribullePacket(TribulleNew.Send.ET_SignaleChangementAvatar, new ByteArray().writeString(this.client.getPlayerName()).writeInt(avatarId));
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatChangementAvatar, new ByteArray().writeInt(0).writeByte(1));
    }

    /**
     * Changes the player's gender.
     * @param tribulleId The tribulle id.
     * @param genderId The gender id.
     */
    private void sendChangePlayerGender(final long tribulleId, byte genderId) {
        this.client.getAccount().setPlayerGender(genderId);

        this.sendTribullePacket(TribulleNew.Send.ET_SignaleChangementGenre, new ByteArray().writeByte(genderId));
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatChangementGenre, new ByteArray().writeInt(tribulleId).writeByte(1));
        for(String friendName : this.client.getAccount().getFriendList()) {
            Client friendClient = this.server.getPlayers().get(friendName);
            if(friendClient != null && friendClient.getAccount().getFriendList().contains(this.client.getPlayerName())) {
                if(friendClient.isOpenFriendList) {
                    friendClient.getParseTribulleInstance().sendFriendList(-1, true);
                }
            }
        }

        if(!this.client.getAccount().getTribeName().isEmpty()) {
            Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
            for(String member : myTribe.getTribeMembers()) {
                Client memberClient = this.server.getPlayers().get(member);
                if(memberClient != null) {
                    if(memberClient.isOpenTribe) {
                        memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresMembre, this.buildTribeMemberInfo(this.client.getAccount().getPlayerName()));
                    }
                }
            }
        }
    }

    /**
     * Changes the tribe house's map code.
     * @param tribulleId The tribulle id.
     * @param mapCode The map code to change.
     */
    private void sendChangeTribeHouseMap(final long tribulleId, final int mapCode) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.CHANGE_TRIBE_HOUSE_MAP))
            return;


        /// TODO: [Unimplemented] modules-parsetribulle->sendChangeTribeHouseMap
        throw new RuntimeException("[Unimplemented] modules-parsetribulle->sendChangeTribeHouseMap");
    }

    /**
     * Changes the tribe leader.
     * @param tribulleId The tribulle id.
     * @param playerName The name of the new leader.
     */
    private void sendChangeTribeLeader(final long tribulleId, final String playerName) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.LEADER))
            return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        Account playerAccount = this.server.getPlayerAccount(playerName);
        playerAccount.setTribeRank(myTribe.getTribeRanks().getLast());
        playerAccount.save();
        this.client.getAccount().setTribeRank(myTribe.getTribeRanks().get(myTribe.getTribeRanks().size() - 2));
        this.client.getAccount().save();
        myTribe.save();
        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresMembre, this.buildTribeMemberInfo(playerName));
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresMembre, this.buildTribeMemberInfo(this.client.getAccount().getPlayerName()));
                }
            }
        }


        myTribe.getTribeHistory().add(new TribeHistoricEntry(5, String.format("{\"auteur\":\"%s\",\"cible\":\"%s\",\"rang\":\"%s\"}", this.client.getPlayerName(), playerName, playerAccount.getTribeRank().getName())));
        myTribe.getTribeHistory().add(new TribeHistoricEntry(5, String.format("{\"auteur\":\"%s\",\"cible\":\"%s\",\"rang\":\"%s\"}", this.client.getPlayerName(), this.client.getPlayerName(), this.client.getAccount().getTribeRank().getName())));

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatDesignerChef, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the rank of given player in the tribe.
     * @param tribulleId The tribulle id.
     * @param playerName The player's name.
     * @param rankPos The rank position.
     */
    private void sendChangeTribeMemberRank(final long tribulleId, final String playerName, final int rankPos) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.CHANGE_MEMBERS_RANK))
            return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        if(myTribe.getTribeRanks().size() < rankPos) return;

        Account playerAccount = this.server.getPlayerAccount(playerName);
        if(playerAccount.getTribeRank().getPosition() > rankPos) return;

        playerAccount.setTribeRank(myTribe.getTribeRanks().get(rankPos));
        myTribe.getTribeHistory().add(new TribeHistoricEntry(5, String.format("{\"auteur\":\"%s\",\"cible\":\"%s\",\"rang\":\"%s\"}", this.client.getPlayerName(), playerName, myTribe.getTribeRanks().get(rankPos).getName())));
        myTribe.save();
        playerAccount.save();

        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementRang, new ByteArray().writeString(this.client.getPlayerName()).writeString(playerName).writeString(playerAccount.getTribeRank().getName()));
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresMembre, this.buildTribeMemberInfo(playerName));
                }
            }
        }

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatAffecterRang, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the tribe's greeting message.
     * @param tribulleId The tribulle id.
     * @param message The message.
     */
    private void sendChangeTribeMessage(final long tribulleId, final String message) {
        if(this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.EDIT_TRIBE_MESSAGE)) 
			return;

        if(message.length() > 500) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatChangerMessageJour, new ByteArray().writeInt(tribulleId).writeByte(22));
            return;
        }

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        myTribe.setTribeMessage(message);
        myTribe.getTribeHistory().add(new TribeHistoricEntry(6, String.format("{\"auteur\":\"%s\"}", this.client.getPlayerName())));
		myTribe.save();
        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementMessageJour, new ByteArray().writeString(this.client.getPlayerName()).writeString(Utils.formatText(message)));
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                }
            }
        }
    }

    /**
     * Changes the tribe's rank.
     * @param tribulleId The tribulle id.
     * @param rankPos The rank position.
     * @param rankName The rank name.
     */
    private void sendChangeTribeRankName(final long tribulleId, final byte rankPos, final String rankName) {
        if(this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.EDIT_GLOBAL_RANKS)) 
			return;

        if(rankName.isEmpty() || !rankName.matches("^[ a-zA-Z0-9]*$") || !Utils.formatText(rankName).equals(rankName)) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatChangementNomRang, new ByteArray().writeInt(tribulleId).writeByte(8));
            return;
        }

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        if(myTribe.getTribeRanks().stream().anyMatch(rank -> rank.getName().equals(rankName))) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatChangementNomRang, new ByteArray().writeInt(tribulleId).writeByte(9));
            return;
        }

        myTribe.getTribeRanks().get(rankPos).setName(rankName);
        myTribe.save();

        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                }
            }
        }

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatChangementNomRang, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the rank permission.
     * @param tribulleId The tribulle id.
     * @param rankPos The rank position.
     * @param rankPerm The rank permission.
     * @param isDisabled Is disabled permission.
     */
    private void sendChangeTribeRankPermission(final long tribulleId, final int rankPos, final int rankPerm, final boolean isDisabled) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        myTribe.getTribeRanks().get(rankPos).setPerm(rankPerm - 1, !isDisabled);
        myTribe.save();
        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                }
            }
        }

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatChangementDroitsRang, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the position of given tribe rank.
     * @param tribulleId The tribulle id.
     * @param prevPos The previous rank position.
     * @param nextPos The next rank position.
     */
    private void sendChangeTribeRankPosition(final long tribulleId, final int prevPos, final int nextPos) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        if(myTribe.getTribeRanks().size() < prevPos || myTribe.getTribeRanks().size() < nextPos) return;

        TribeRank rank = myTribe.getTribeRanks().get(prevPos);
        rank.setPosition(nextPos);
        TribeRank rank2 = myTribe.getTribeRanks().get(nextPos);
        rank2.setPosition(prevPos);

        myTribe.getTribeRanks().set(prevPos, rank2);
        myTribe.getTribeRanks().set(nextPos, rank);
        myTribe.save();
        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                }
            }
        }

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatInverserRang, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Closes the friend list.
     * @param tribulleId Tribulle id.
     */
    private void sendCloseFriendList(final long tribulleId) {
        this.client.isOpenFriendList = false;

        ByteArray byteArray = new ByteArray();
        byteArray.writeInt(tribulleId);
        byteArray.writeByte(1);

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeFermetureListeAmis, byteArray);
    }

    /**
     * Closes the channel name.
     * @param tribulleId Tribulle id.
     * @param chatName The chat name.
     */
    private void sendCloseGlobalChannel(final long tribulleId, final String chatName) {
        if (this.server.getChats().containsKey(chatName)) {
            this.server.getChats().get(chatName).remove(this.client.getPlayerName());

            this.sendTribullePacket(TribulleNew.Send.ET_SignalementQuitterCanalPublique, new ByteArray().writeString(chatName));
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatQuitterCanalPublique, new ByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Closes the current tribe.
     * @param tribulleId The tribulle id.
     */
    private void sendCloseTribe(final long tribulleId) {
        if(this.client.getAccount().getTribeName().isEmpty()) return;

        this.client.isOpenTribe = false;
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatFermetureInterfaceTribu, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Creates a new chat in the game.
     * @param tribulleId The tribulle id.
     * @param chatName The chat name.
     */
    private void sendCreateNewGlobalChannel(final long tribulleId, final String chatName) {
        if(!chatName.matches("^[a-zA-Z0-9_.-]*$") || (chatName.equals("vip") && !this.client.isVip() && this.client.getAccount().getPrivLevel() < 5)) {
            // invalid chat name, or person is not vip to access #vip
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatRejoindreCanalPublique, new ByteArray().writeInt(tribulleId).writeByte(8));
            return;
        }

        if (!this.server.getChats().containsKey(chatName)) {
            this.server.getChats().put(chatName, new ArrayList<>());
        }

        if (this.server.getChats().get(chatName).size() > 100 && !chatName.equals("vip")) {
            // maximum members in the chat.
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatRejoindreCanalPublique, new ByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        if (!this.server.getChats().get(chatName).contains(this.client.getPlayerName())) {
            this.server.getChats().get(chatName).add(this.client.getPlayerName());

            this.sendTribullePacket(TribulleNew.Send.ET_SignalementRejoindreCanalPublique, new ByteArray().writeString(chatName));
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatRejoindreCanalPublique, new ByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Creates a new tribe.
     * @param tribulleId The tribulle id.
     * @param tribeName The tribe name.
     */
    private void sendCreateNewTribe(final long tribulleId, final String tribeName) {
        if(!this.client.getAccount().getTribeName().isEmpty()) {
            return;
        }

        if (tribeName.isEmpty() || !tribeName.matches("^[ a-zA-Z0-9]*$") || !Utils.formatText(tribeName).equals(tribeName)) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeCreerTribu, new ByteArray().writeInt(tribulleId).writeByte(8));
            return;
        }

        if(this.server.getTribeByName(tribeName) != null) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeCreerTribu, new ByteArray().writeInt(tribulleId).writeByte(9));
            return;
        }

        if(this.client.getAccount().getShopCheeses() < 500) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeCreerTribu, new ByteArray().writeInt(tribulleId).writeByte(14));
            return;
        }

        this.client.getAccount().setShopCheeses(this.client.getAccount().getShopCheeses() - 500);

        Tribe myTribe = new Tribe(this.client.getPlayerName(), tribeName);
        myTribe.save();
        this.server.getCachedTribes().put(tribeName, myTribe);

        this.client.getAccount().setTribeName(tribeName);
        this.client.getAccount().setTribeRank(myTribe.getTribeRanks().getLast());

        this.sendTribullePacket(TribulleNew.Send.ET_SignaleInformationsMembreTribu, this.buildTribeConnectionInfo(this.client.getAccount().getTribeName(), this.client.getAccount().getTribeRank()));
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeCreerTribu, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Creates a new tribe rank.
     * @param tribulleId The tribulle id.
     * @param rankName The rank name.
     */
    private void sendCreateTribeRank(final long tribulleId, final String rankName) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        if(rankName.isEmpty() || !rankName.matches("^[ a-zA-Z0-9]*$") || !Utils.formatText(rankName).equals(rankName)) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutRang, new ByteArray().writeInt(tribulleId).writeByte(8));
            return;
        }

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        if(myTribe.getTribeRanks().stream().anyMatch(rank -> rank.getName().equals(rankName))) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutRang, new ByteArray().writeInt(tribulleId).writeByte(9));
            return;
        }

        if(myTribe.getTribeRanks().size() + 1 > 100) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutRang, new ByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        TribeRank rank = new TribeRank();
        rank.setPosition(1);
        rank.setName(rankName);
        myTribe.getTribeRanks().add(1, rank);
        for (TribeRank tribeRank : myTribe.getTribeRanks()) {
            if (tribeRank.getPosition() > 0) {
                tribeRank.setPosition(tribeRank.getPosition() + 1);
            }
        }

        myTribe.save();
        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                }
            }
        }
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatAjoutRang, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Deletes a tribe rank from the tribe.
     * @param tribulleId The tribulle id.
     * @param position The rank position.
     */
    private void sendDeleteTribeRank(final long tribulleId, final byte position) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        if(myTribe.getTribeRanks().size() < position || myTribe.getTribeRanks().size() < 2) return;

        TribeRank oldRank = myTribe.getTribeRanks().get(position);
        TribeRank newRank = myTribe.getTribeRanks().get(position - 1);
        myTribe.getTribeRanks().remove(position);
        for (TribeRank tribeRank : myTribe.getTribeRanks()) {
            if (tribeRank.getPosition() > position) {
                tribeRank.setPosition(tribeRank.getPosition() - 1);
            }
        }

        for (String member : myTribe.getTribeMembers()) {
            Account memberAccount = this.server.getPlayerAccount(member);
            if(memberAccount.getTribeRank() == oldRank) {
                myTribe.getTribeHistory().add(new TribeHistoricEntry(5, String.format("{\"auteur\":\"%s\",\"cible\":\"%s\",\"rang\":\"%s\"}", this.client.getPlayerName(), member, newRank.getName())));
                memberAccount.setTribeRank(newRank);
            }
        }

        myTribe.save();
        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                }
            }
        }

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatSupprimeRang, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sets silence mode in current player.
     * @param tribulleId The tribulle id
     * @param silenceType The silence type.
     * @param silenceMessage The silence message.
     */
    private void sendDefineModeSilence(final long tribulleId, final byte silenceType, final String silenceMessage) {
        this.client.silenceType = silenceType;
        this.client.silenceMessage = silenceMessage;
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatDefinirModeSilence, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Dissolves the current tribe.
     * @param tribulleId The tribulle id.
     */
    private void sendDissolveTribe(final long tribulleId) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.LEADER))
            return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        for(String member : myTribe.getTribeMembers()) {
            Account memberAccount = this.server.getPlayerAccount(member);
            memberAccount.setTribeName("");
            memberAccount.setTribeRank(null);
            memberAccount.save();
            if(this.server.checkIsConnected(member)) {
                this.server.getPlayers().get(member).getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleInformationsMembreTribu, this.buildTribeConnectionInfo("", null));
            }
        }

        this.server.getCachedTribes().remove(myTribe.getTribeName());
        myTribe.delete();
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeDissoudreTribu, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends the friend list.
     */
    private void sendFriendList(final long tribulleId, boolean isOpen) {
        ByteArray data = new ByteArray();
        var friendList = this.client.getAccount().getFriendList();

        this.client.isOpenFriendList = true;
        data.writeBytes(this.buildFriendInfo(this.client.getAccount().getSoulmate(), this.client.getPlayerName()).toByteArray());
        data.writeShort((short)friendList.size());
        for(String friendName : friendList) {
            data.writeBytes(this.buildFriendInfo(friendName, this.client.getPlayerName()).toByteArray());
        }

        this.sendTribullePacket(TribulleNew.Send.ET_SignalementListeAmis, data);
        if(!isOpen) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeOuvertureListeAmis, new ByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Sends a modification in friendship.
     * @param friendName The friend name.
     * @param type (Is online or offline for send message or -1 for not send the connection message).
     */
    public void sendFriendModification(String friendName, int type) {
        this.sendTribullePacket(TribulleNew.Send.ET_SignalementModificationAmi, this.buildFriendInfo(friendName, this.client.getPlayerName()));
        if(type == 0) {
            this.sendTribullePacket(TribulleNew.Send.ET_SignalementDeconnexionAmi, new ByteArray().writeString(friendName));
        }else if(type == 1) {
            this.sendTribullePacket(TribulleNew.Send.ET_SignalementConnexionAmi, new ByteArray().writeString(friendName));
        }
    }

    /**
     * Sends a message in the given channel name.
     * @param tribulleId The tribulle id.
     * @param chatName The chat name.
     * @param message The message to send.
     */
    private void sendGlobalChannelMessage(final long tribulleId, final String chatName, final String message) {
        if(chatName.equals("vip") && (!this.client.isVip() && this.client.getAccount().getPrivLevel() < 5)) return;

        if(this.client.getAccount().getPlayedTime() / 3600 < 4) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessageChat, new ByteArray().writeInt(tribulleId).writeByte(28));
            return;
        }

        if(message.length() > 90) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessageChat, new ByteArray().writeInt(tribulleId).writeByte(22));
            return;
        }

        Sanction mySanction = this.server.getLatestSanction(this.client.getPlayerName(), "mutedef");
        if(mySanction == null) {
            mySanction = this.server.getLatestSanction(this.client.getPlayerName(), "mutejeu");
        }

        if(mySanction != null) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessageChat, new ByteArray().writeInt(tribulleId).writeByte(23));
            return;
        }

        if(this.client.chatMessageTimer.getRemainingTime() <= 0) {
            if(this.server.getChats().containsKey(chatName) && this.server.getChats().get(chatName).contains(this.client.getPlayerName())) {
                for (String player : this.server.getChats().get(chatName)) {
                    this.server.getPlayers().get(player).getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignalementMessageChat, new ByteArray().writeString(this.client.getPlayerName()).writeInt(Langue.fromValue(this.client.playerCommunity)+1).writeString(chatName).writeString(Utils.formatText(message)));
                }
            }

            this.client.chatMessageTimer.schedule(() -> {}, TimeUnit.SECONDS);
        } else {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessageChat, new ByteArray().writeInt(tribulleId).writeByte(24));
        }
    }

    /**
     * Sends the member list in the given channel name.
     * @param tribulleId The tribulle id.
     * @param chatName The chat name.
     */
    private void sendGlobalChannelMembersList(final long tribulleId, final String chatName) {
        if(this.server.getChats().get(chatName) != null) {
            ByteArray data = new ByteArray().writeInt(tribulleId).writeByte(1);
            data.writeShort((short)this.server.getChats().get(chatName).size());
            for(String member : this.server.getChats().get(chatName)) {
                data.writeString(member);
            }
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatListerCanalPublique, data);
        }
    }

    /**
     * Sends the platform connection to tribulle.
     */
    public void sendIdentificationService() {
        ByteArray data = new ByteArray();
        var friendList = this.client.getAccount().getFriendList();
        var ignoredList = this.client.getAccount().getIgnoredList();

        data.writeByte(this.client.getAccount().getPlayerGender());
        data.writeInt(this.client.getAccount().getId());
        data.writeBytes(this.buildFriendInfo(this.client.getAccount().getSoulmate(), this.client.getPlayerName()).toByteArray());
        data.writeShort((short)friendList.size());
        for(String friendName : friendList) {
            data.writeBytes(this.buildFriendInfo(friendName, this.client.getPlayerName()).toByteArray());
        }

        data.writeShort((short)ignoredList.size());
        for(String ignoredName : ignoredList) {
            data.writeString(ignoredName);
        }

        data.writeBytes(this.buildTribeConnectionInfo(this.client.getAccount().getTribeName(), this.client.getAccount().getTribeRank()).toByteArray());
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatIdentificationService, data);
    }

    /**
     * Answers to the marriage invitation
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     * @param answer The answer (True for yes, False for no).
     */
    private void sendMarriageAnswer(final long tribulleId, final String playerName, final boolean answer) {
        Client playerClient = this.server.getPlayers().get(playerName);
        this.client.currentMarriageInvite = "";

        if(playerClient == null) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatReponseDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        if(!answer) {
            this.sendTribullePacket(TribulleNew.Send.ET_SignalementRefusMariage, new ByteArray().writeString(this.client.getPlayerName()));
        } else {
            this.client.getAccount().setSoulmate(playerName);
            playerClient.getAccount().setSoulmate(this.client.getPlayerName());

            this.sendTribullePacket(TribulleNew.Send.ET_SignalementMariage, new ByteArray().writeString(playerName));
            playerClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignalementMariage, new ByteArray().writeString(this.client.getPlayerName()));

            if(this.client.isOpenFriendList) {
                this.sendFriendList(-1, true);
            }

            if(playerClient.isOpenFriendList) {
                playerClient.getParseTribulleInstance().sendFriendList(-1, true);
            }

            this.client.marriageTimer.schedule(() -> {}, TimeUnit.HOURS);
        }

        playerClient.currentMarriageInvite = "";
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatReponseDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Divorces the current marriage.
     * @param tribulleId The tribulle id,
     */
    private void sendMarriageDivorce(final long tribulleId) {
        String soulmate = this.client.getAccount().getSoulmate();
        if(!soulmate.isEmpty()) {
            Account soulmateClient = this.server.getPlayerAccount(soulmate);
            if(soulmateClient == null) {
                return;
            }

            this.sendTribullePacket(TribulleNew.Send.ET_SignalementDivorce, new ByteArray().writeString(soulmate).writeBoolean(true));
            this.client.getAccount().setSoulmate("");
            soulmateClient.setSoulmate("");

            if(this.server.checkIsConnected(soulmate)) {
                this.server.getPlayers().get(soulmate).getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignalementDivorce, new ByteArray().writeString(this.client.getPlayerName()).writeBoolean(false));
                if(this.server.getPlayers().get(soulmate).isOpenFriendList) {
                    this.server.getPlayers().get(soulmate).getParseTribulleInstance().sendFriendList(-1, true);
                }
            }

            this.sendFriendList(-1, true);
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeDivorce, new ByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Sends marriage invitation to the given player.
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     */
    private void sendMarriageInvitation(final long tribulleId, final String playerName) {
        Client playerClient = this.server.getPlayers().get(playerName);
        if(playerClient == null || playerClient.isGuest()) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(11));
            return;
        }

        if(!playerClient.getAccount().getSoulmate().isEmpty()) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(15));
            return;
        }

        if(!this.client.currentMarriageInvite.isEmpty() || !playerClient.currentMarriageInvite.isEmpty()) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(6));
            return;
        }

        if(this.client.getAccount().getIgnoredList().contains(playerName)) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        if(playerClient.marriageTimer.getRemainingTime() > 0) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(32));
            return;
        }

        if(this.client.marriageTimer.getRemainingTime() > 0) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(30));
            return;
        }

        this.client.currentMarriageInvite = playerName;
        playerClient.currentMarriageInvite = this.client.getPlayerName();
        playerClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignalementDemandeEnMariage, new ByteArray().writeString(this.client.getPlayerName()));
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeEnMariage, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends the ignored list.
     */
    private void sendIgnoredList(final long tribulleId) {
        ByteArray byteArray = new ByteArray();
        var info = this.client.getAccount().getIgnoredList();

        byteArray.writeInt(tribulleId);
        byteArray.writeShort((short)info.size());
        for(String playerName : info) {
            byteArray.writeString(playerName);
        }

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatConsultationListeNoire, byteArray);
    }

    /**
     * Opens the tribe.
     * @param tribulleId The tribulle id.
     */
    private void sendOpenTribe(final long tribulleId, final boolean isConnected) {
        if(this.client.getAccount().getTribeName().isEmpty()) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatOuvertureInterfaceTribu, new ByteArray().writeInt(tribulleId).writeByte(17));
            return;
        }

        this.client.isOpenTribe = true;

        this.sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), isConnected));
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatOuvertureInterfaceTribu, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends a private player message.
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     * @param message The message to send.
     */
    private void sendPlayerWhisperMessage(final long tribulleId, final String playerName, final String message) {
        if(this.client.isGuest()) {
            this.client.sendPacket(new C_TranslationMessage("", "$Créer_Compte_Parler"));
            return;
        }

        if(!this.server.checkIsConnected(playerName) || playerName.startsWith("*")) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessagePrive, new ByteArray().writeInt(tribulleId).writeByte(12).writeString(""));
            return;
        }

        if(this.client.getAccount().getIgnoredList().contains(playerName)) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessagePrive, new ByteArray().writeInt(tribulleId).writeByte(27).writeString(""));
            return;
        }

        if(this.client.chatMessageTimer.getRemainingTime() > 0) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessagePrive, new ByteArray().writeInt(tribulleId).writeByte(24).writeString(""));
            return;
        }

        Sanction mySanction = this.server.getLatestSanction(this.client.getPlayerName(), "mutedef");
        if(mySanction == null) {
            mySanction = this.server.getLatestSanction(this.client.getPlayerName(), "mutejeu");
        }

        if(mySanction != null) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessagePrive, new ByteArray().writeInt(tribulleId).writeByte(23).writeString(""));
            return;
        }

        if(message.length() > 90) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessagePrive, new ByteArray().writeInt(tribulleId).writeByte(22).writeString(""));
            return;
        }

        Client playerClient = this.server.getPlayers().get(playerName);
        if(this.client.getAccount().getPlayedTime() / 3600 < 5 && !playerClient.getAccount().getFriendList().contains(this.client.getPlayerName()) && this.client.getAccount().getPrivLevel() < 5) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessagePrive, new ByteArray().writeInt(tribulleId).writeByte(28).writeString(""));
            return;
        }

        if(this.client.getAccount().getPrivLevel() < 5 && !playerName.equals(this.client.getPlayerName()) && (playerClient.silenceType == 2 || ((playerClient.silenceType == 1 && !playerClient.getAccount().getFriendList().contains(this.client.getPlayerName()))))) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessagePrive, new ByteArray().writeInt(tribulleId).writeByte(25).writeString(playerClient.silenceMessage));
            return;
        }

        this.sendTribullePacket(TribulleNew.Send.ET_SignalementMessagePrive, new ByteArray().writeString(this.client.getPlayerName()).writeInt(Langue.fromValue(this.client.playerCommunity) + 1).writeString(playerClient.getPlayerName()).writeString(Utils.formatText(message)));
        if(!playerName.equals(this.client.getPlayerName())) {
            playerClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignalementMessagePrive, new ByteArray().writeString(this.client.getPlayerName()).writeInt(Langue.fromValue(this.client.playerCommunity) + 1).writeString(playerClient.getPlayerName()).writeString(Utils.formatText(message)));
        }

        // #ChatLog
        Deque<String[]> messages = new ArrayDeque<>(60);
        messages.add(new String[]{new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), message});
        this.server.getWhisperMessages().putIfAbsent(this.client.getPlayerName(), new Object2ObjectOpenHashMap<>());
        this.server.getWhisperMessages().get(this.client.getPlayerName()).put(playerName, messages);

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessagePrive, new ByteArray().writeInt(tribulleId).writeByte(1).writeString(""));
    }

    /**
     * Removes a friend from the friend list.
     * @param tribulleId The tribulle id.
     * @param friendName The friend's name.
     */
    private void sendRemoveFriend(final long tribulleId, final String friendName) {
        if(this.client.getAccount().getFriendList().contains(friendName)) {
            Account friendAccount = this.server.getPlayerAccount(friendName);
            this.client.getAccount().getFriendList().remove(friendName);

            this.sendTribullePacket(TribulleNew.Send.ET_SignalementSuppressionAmi, new ByteArray().writeInt(friendAccount.getId()));
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatSuppressionAmi, new ByteArray().writeInt(tribulleId).writeByte(1));
            if(this.server.checkIsConnected(friendName)) {
                this.server.getPlayers().get(friendName).getParseTribulleInstance().sendFriendModification(this.client.getPlayerName(), -1);
            }
        }
    }

    /**
     * Removes a player from block list.
     * @param tribulleId The tribulle id.
     * @param ignoredPlayer The given player name.
     */
    private void sendRemoveIgnored(final long tribulleId, final String ignoredPlayer) {
        if(this.client.getAccount().getIgnoredList().contains(ignoredPlayer)) {
            this.client.getAccount().getIgnoredList().remove(ignoredPlayer);

            this.sendTribullePacket(TribulleNew.Send.ET_ResultatSuppressionListeNoire, new ByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Sends the tribe history.
     * @param tribulleId The tribulle id.
     * @param startIndex The start index.
     * @param limit The limit.
     */
    private void sendTribeHistory(final long tribulleId, final int startIndex, final int limit) {
        if(this.client.getAccount().getTribeName().isEmpty()) return;

        ByteArray historicData = new ByteArray();
        int count = 0;
        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());

        List<TribeHistoricEntry> reverseHistoric = new ArrayList<>(myTribe.getTribeHistory());
        Collections.reverse(reverseHistoric);
        for (int i = startIndex; i < Math.min(reverseHistoric.size(), startIndex + limit); i++) {
            TribeHistoricEntry entry = reverseHistoric.get(i);
            historicData.writeInt(entry.getDate());
            historicData.writeInt(entry.getType());
            historicData.writeString(entry.getInformation());
            count += 1;
        }

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatHistoriqueTribu, new ByteArray().writeInt(tribulleId).writeShort((short)count).writeBytes(historicData.toByteArray()).writeInt(reverseHistoric.size()));
    }

    /**
     * Sends tribe invite to the given player.
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     */
    private void sendTribeMemberInvitation(final long tribulleId, final String playerName) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.INVITE_MEMBERS))
            return;

        Client player = this.server.getPlayers().get(playerName);
        if (player == null || player.isGuest()) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(11));
            return;
        }

        if (this.server.getTribeByName(this.client.getAccount().getTribeName()).getTribeMembers().size() >= 2500) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        if (!player.getAccount().getTribeName().isEmpty()) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(18));
            return;
        }

        if (!player.currentTribeInvite.isEmpty()) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(6));
            return;
        }

        player.currentTribeInvite = this.client.getPlayerName();
        player.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleInvitationTribu, new ByteArray().writeString(this.client.getPlayerName()).writeString(this.client.getAccount().getTribeName()));
        this.sendTribullePacket(TribulleNew.Send.ET_ResultatInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Answers to the tribe invite.
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     * @param isAccepted Is he accepted?
     */
    private void sendTribeMemberInvitationAnswer(final long tribulleId, final String playerName, final boolean isAccepted) {
        if(playerName.equals(this.client.currentTribeInvite)) {
            Client playerClient = this.server.getPlayers().get(playerName);
            if(playerClient == null) {
                this.sendTribullePacket(TribulleNew.Send.ET_ResultatRepondsInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(17));
                return;
            }

            if(!this.client.getAccount().getTribeName().isEmpty()) {
                this.sendTribullePacket(TribulleNew.Send.ET_ResultatRepondsInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(18));
                return;
            }

            Tribe myTribe = this.server.getTribeByName(playerClient.getAccount().getTribeName());
            if(myTribe.getTribeMembers().size() >= 2500) {
                this.sendTribullePacket(TribulleNew.Send.ET_ResultatRepondsInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(7));
                return;
            }

            if(isAccepted) {
                myTribe.getTribeMembers().add(this.client.getPlayerName());
                myTribe.getTribeHistory().add(new TribeHistoricEntry(2, String.format("{\"auteur\":\"%s\",\"membreAjoute\":\"%s\"}", playerName, this.client.getPlayerName())));
                this.client.getAccount().setTribeName(myTribe.getTribeName());
                this.client.getAccount().setTribeRank(myTribe.getTribeRanks().getFirst());

                myTribe.save();
                playerClient.getAccount().save();
                for(String member : myTribe.getTribeMembers()) {
                    Client memberClient = this.server.getPlayers().get(member);
                    if(memberClient != null) {
                        memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleNouveauMembre, new ByteArray().writeString(this.client.getPlayerName()));
                        if(memberClient.isOpenTribe) {
                            memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                        }
                    }
                }
                this.sendTribullePacket(TribulleNew.Send.ET_SignaleInformationsMembreTribu, this.buildTribeConnectionInfo(this.client.getAccount().getTribeName(), this.client.getAccount().getTribeRank()));
            }

            this.client.currentTribeInvite = "";
            playerClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleReponseInvitationTribu, new ByteArray().writeString(this.client.getPlayerName()).writeBoolean(isAccepted));
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatRepondsInvitationTribu, new ByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Sends when somebody gets kicked from the tribe.
     * @param tribulleId The tribulle id.
     * @param playerName The player name to kick.
     */
    private void sendTribeMemberKick(final long tribulleId, final String playerName) {
        if (this.client.getAccount().getTribeRank() == null || !this.client.getAccount().getTribeRank().hasPerm(TribeRank.TribePerms.EXCLUDE_MEMBERS))
            return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        if(this.server.checkIsConnected(playerName)) {
            this.server.getPlayers().get(playerName).isOpenTribe = false;
            this.server.getPlayers().get(playerName).getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleExclusionMembre, new ByteArray().writeString(playerName).writeString(this.client.getPlayerName()));
        }

        myTribe.getTribeMembers().remove(playerName);
        myTribe.getTribeHistory().add(new TribeHistoricEntry(3, String.format("{\"auteur\":\"%s\",\"membreExclu\":\"%s\"}", this.client.getPlayerName(), playerName)));
        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleExclusionMembre, new ByteArray().writeString(playerName).writeString(this.client.getPlayerName()));
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                }
            }
        }

        Account playerAccount = this.server.getPlayerAccount(playerName);
        playerAccount.setTribeName("");
        playerAccount.setTribeRank(null);
        playerAccount.save();

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatExclureMembre, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends when member leave the current tribe.
     * @param tribulleId The tribulle id.
     */
    private void sendTribeMemberLeave(final long tribulleId) {
        if (this.client.getAccount().getTribeRank() == null)
            return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        if (this.client.getAccount().getTribeRank() == myTribe.getTribeRanks().getLast()) {
            this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeQuitterTribu, new ByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        myTribe.getTribeMembers().remove(this.client.getPlayerName());
        myTribe.getTribeHistory().add(new TribeHistoricEntry(4, String.format("{\"membreParti\":\"%s\"}", this.client.getPlayerName())));
        this.sendTribullePacket(TribulleNew.Send.ET_SignaleDepartMembre, new ByteArray().writeString(this.client.getPlayerName()));
        myTribe.save();

        for(String member : myTribe.getTribeMembers()) {
            Client memberClient = this.server.getPlayers().get(member);
            if(memberClient != null) {
                memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleDepartMembre, new ByteArray().writeString(this.client.getPlayerName()));
                if(memberClient.isOpenTribe) {
                    memberClient.getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
                }
            }
        }

        this.client.getAccount().setTribeName("");
        this.client.getAccount().setTribeRank(null);
        this.client.getAccount().save();
        this.client.isOpenTribe = false;

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatDemandeQuitterTribu, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends a notification when tribe member connects & disconnects from the game.
     * @param tribeMember The tribe member.
     * @param type Is online or offline?
     * @param isOpenTribe Is the tribe open to update.
     */
    public void sendTribeMemberModification(String tribeMember, int type, boolean isOpenTribe) {
        if(type == 1) {
            this.sendTribullePacket(TribulleNew.Send.ET_SignaleConnexionMembre, new ByteArray().writeString(tribeMember));
        }
        else if(type == 0) {
            this.sendTribullePacket(TribulleNew.Send.ET_SignaleDeconnexionMembre, new ByteArray().writeString(tribeMember));
        }

        if(isOpenTribe) {
            this.sendTribullePacket(TribulleNew.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.client.getAccount().getTribeName(), false));
        }
    }

    /**
     * Sends a message in #Tribe chat.
     * @param tribulleId The tribulle id.
     * @param message The message.
     */
    private void sendTribeMessage(final long tribulleId, final String message) {
        if(this.client.getAccount().getTribeName().isEmpty()) return;

        Tribe myTribe = this.server.getTribeByName(this.client.getAccount().getTribeName());
        for(String member : myTribe.getTribeMembers()) {
            if(this.server.checkIsConnected(member)) {
                this.server.getPlayers().get(member).getParseTribulleInstance().sendTribullePacket(TribulleNew.Send.ET_SignalementMessageTribu, new ByteArray().writeString(this.client.getPlayerName()).writeString(Utils.formatText(message)));
            }
        }

        this.sendTribullePacket(TribulleNew.Send.ET_ResultatEnvoiMessageTribu, new ByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends a tribulle packet.
     * @param tribulleCode Tribulle code.
     * @param data The packet data.
     */
    private void sendTribullePacket(final short tribulleCode, final ByteArray data) {
        this.client.sendTribullePacket(new TribullePacket() {
            @Override
            public short getTribulleCode() {
                return tribulleCode;
            }

            @Override
            public boolean getIsLegacy() {
                return false;
            }

            @Override
            public byte[] getPacket() {
                return data.toByteArray();
            }
        }, false);
    }

    /**
     * Helper function for build the friend info.
     * @param friendName The friend name.
     * @param playerName The player name.
     * @return A packet that contains info about player's friend.
     */
    private ByteArray buildFriendInfo(String friendName, String playerName) {
        if(friendName.isEmpty()) {
            return new ByteArray().writeInt(0).writeString("").writeByte(0).writeInt(0).writeBoolean(false).writeBoolean(false).writeInt(1).writeString("").writeInt(0);
        }

        Account friendAccount;
        if(this.server.checkIsConnected(friendName)) {
            Client friendClient = this.server.getPlayers().get(friendName);
            friendAccount = friendClient.getAccount();

            return new ByteArray().writeInt(friendAccount.getId()).writeString(friendAccount.getPlayerName()).writeByte(friendAccount.getPlayerGender()).writeInt(friendAccount.getAvatarId()).writeBoolean(friendAccount.getFriendList().contains(playerName)).writeBoolean(true).writeInt(4).writeString(friendClient.getRoomName()).writeInt(friendAccount.getLastOn());
        }

        if(this.server.getCachedAccounts().containsKey(friendName)) {
            friendAccount = this.server.getCachedAccounts().get(friendName);
        } else {
            friendAccount = DBUtils.findAccountByNickname(friendName);
            this.server.getCachedAccounts().put(friendName, friendAccount);
        }

        return new ByteArray().writeInt(friendAccount.getId()).writeString(friendAccount.getPlayerName()).writeByte(friendAccount.getPlayerGender()).writeInt(friendAccount.getAvatarId()).writeBoolean(friendAccount.getFriendList().contains(playerName)).writeBoolean(false).writeInt(0).writeString("").writeInt(friendAccount.getLastOn());
    }

    /**
     * Helper function for build the connection info of a tribe member.
     * @param tribeName The tribe name.
     * @param tribeRank The tribe rank.
     * @return A packet that contains connection info about tribe member when he connects to the game.
     */
    private ByteArray buildTribeConnectionInfo(String tribeName, TribeRank tribeRank) {
        if(tribeName.isEmpty()) {
            return new ByteArray().writeString("").writeInt(0).writeString("").writeInt(0).writeString("").writeInt(0);
        }
        Tribe myTribe = this.server.getTribeByName(tribeName);
        return new ByteArray().writeString(myTribe.getTribeName()).writeInt(myTribe.getId()).writeString(myTribe.getTribeMessage()).writeInt(myTribe.getTribeHouseMap()).writeString(tribeRank.getName()).writeInt(tribeRank.getPerms());
    }

    /**
     * Helper function for build the tribe member info.
     * @param playerName The tribe member.
     * @return A packet that contains information about the player in the tribe.
     */
    private ByteArray buildTribeMemberInfo(String playerName) {
        Client playerClient = this.server.getPlayers().get(playerName);
        Account accountClient = this.server.getPlayerAccount(playerName);

        ByteArray data = new ByteArray();
        data.writeInt(accountClient.getId());
        data.writeString(accountClient.getPlayerName());
        data.writeByte(accountClient.getPlayerGender());
        data.writeInt(accountClient.getAvatarId());
        data.writeInt(playerClient != null ? 0 : accountClient.getLastOn());
        data.writeByte(accountClient.getTribeRank().getPosition());
        data.writeInt(playerClient != null ? 4 : 1);
        data.writeString(playerClient != null ? playerClient.getRoomName() : "");
        return data;
    }

    /**
     * Helper function for build the info of all tribe members.
     * @param tribeName The tribe name.
     * @param isConnected Show only connected members.
     * @return A packet that contains information about all tribe members.
     */
    private ByteArray buildTribeMembersInfo(String tribeName, boolean isConnected) {
        if(tribeName.isEmpty()) {
            return new ByteArray().writeInt(0).writeString("").writeString("").writeInt(0).writeShort((short)0).writeShort((short)0);
        }

        Tribe myTribe = this.server.getTribeByName(tribeName);
        ByteArray data = new ByteArray();
        data.writeInt(myTribe.getId());
        data.writeString(myTribe.getTribeName());
        data.writeString(myTribe.getTribeMessage());
        data.writeInt(myTribe.getTribeHouseMap());

        ByteArray data2 = new ByteArray();
        short cnt = 0;
        for(String member : myTribe.getTribeMembers()) {
            if(isConnected && this.server.checkIsConnected(member)) continue;
            if(!isConnected && !this.server.checkIsConnected(member)) continue;

            cnt++;
            data2.writeBytes(this.buildTribeMemberInfo(member).toByteArray());
        }

        data.writeShort(cnt);
        data.writeBytes(data2.toByteArray());
        data.writeShort((short)myTribe.getTribeRanks().size());
        for(var info : myTribe.getTribeRanks()) {
            data.writeString(info.getName());
            data.writeInt(info.getPerms());
        }

        return data;
    }
}