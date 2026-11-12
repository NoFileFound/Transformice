package org.transformice.modules;

// Imports
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.GameCommunity;
import org.transformice.data.objects.PlayerGender;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.ConnectionLogEntry;
import org.transformice.database.data.MapEntry;
import org.transformice.database.data.PlayerEntity;
import org.transformice.database.data.SanctionEntry;
import org.transformice.database.data.TribeEntry;
import org.transformice.modules.impl.ModulePlayer;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;
import org.transformice.packets.PacketTribulleOpcodes;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.PacketSendStaffChatMessage;
import org.transformice.packets.send.informations.PacketSendEmailAddressVerified;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;
import org.transformice.packets.send.legacy.PacketSendChangeTribeHouseResult;
import org.transformice.packets.send.tribulle.PacketSendJoinChatChannel;
import org.transformice.packets.send.tribulle.PacketSendTribulleSwitchProtocol;

public final class ModuleTribulle extends ModulePlayer {
    /**
     * Creates a new tribulle module.
     * @param gameServer The game server.
     * @param gamePlayer The game player.
     */
    public ModuleTribulle(GameServer gameServer, GamePlayer gamePlayer) {
        super(gameServer, gamePlayer);
    }

    /**
     * Broadcasts a tribulle packet.
     * @param tribulleCode The tribulle code.
     * @param packet The packet to send.
     */
    public void broadcastTribullePacket(int tribulleCode, NetByteArray packet) {
        this.player.broadcastPacket(new PSSendPacket(PacketOpcodes.SEND_TRIBULLE) {
            @Override
            public PSByteArray build() throws PSSendPacketException {
                return new PSByteArray().writeShort((short) tribulleCode).writeBytes(packet.toByteArray());
            }
        });
    }

    /**
     * Handles the given tribulle packet.
     * @param tribulleCode The tribulle packet code.
     * @param tribulleId The tribulle id.
     * @param psData The tribulle packet.
     */
    public void handleTribullePacket(int tribulleCode, int tribulleId, NetByteArray psData) {
        switch (tribulleCode) {
            case PacketTribulleOpcodes.Receive.ST_ChangerDeGenre:
                this.sendChangePlayerGender(tribulleId, psData.readByte());
                break;
            case PacketTribulleOpcodes.Receive.ST_AjoutAmi:
                this.sendAddFriend(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_DemandeEnMariage:
                this.sendMarriageInvitation(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_RepondDemandeEnMariage:
                this.sendMarriageAnswer(tribulleId, psData.readUTF(), psData.readBoolean());
                break;
            case PacketTribulleOpcodes.Receive.ST_DemandeDivorce:
                this.sendMarriageDivorce(tribulleId);
                break;
            case PacketTribulleOpcodes.Receive.ST_RetireAmi:
                this.sendRemoveFriend(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_ListeAmis:
                this.sendFriendList(tribulleId, false);
                break;
            case PacketTribulleOpcodes.Receive.ST_FermeeListeAmis:
                this.sendCloseFriendList(tribulleId);
                break;
            case PacketTribulleOpcodes.Receive.ST_AjoutNoire:
                this.sendAddIgnored(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_RetireListeNoire:
                this.sendRemoveIgnored(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_ListeNoire:
                this.sendIgnoredList(tribulleId);
                break;
            case PacketTribulleOpcodes.Receive.ST_EnvoiMessageChat:
                this.sendGlobalChannelMessage(tribulleId, psData.readUTF(), psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_EnvoiMessageTribu:
                this.sendTribeMessage(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_EnvoiMessagePrive:
                this.sendPlayerWhisperMessage(tribulleId, psData.readUTF(), psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_RejoindreCanalPublique:
                this.sendCreateNewGlobalChannel(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_QuitterCanalPublique:
                this.sendCloseGlobalChannel(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_DemandeMembresCanal:
                this.sendGlobalChannelMembersList(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_DefinitModeSilence:
                this.sendDefineModeSilence(tribulleId, psData.readByte(), psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_InvitationTribu:
                this.sendTribeMemberInvitation(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_RepondsInvitationTribu:
                this.sendTribeMemberInvitationAnswer(tribulleId, psData.readUTF(), psData.readBoolean());
                break;
            case PacketTribulleOpcodes.Receive.ST_DemandeQuitterTribu:
                this.sendTribeMemberLeave(tribulleId);
                break;
            case PacketTribulleOpcodes.Receive.ST_DemandeCreerTribu:
                this.sendCreateNewTribe(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_ChangerMessageJour:
                this.sendChangeTribeMessage(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_ChangerCodeMaisonTFM:
                this.sendChangeTribeHouseMap(psData.readInt());
                break;
            case PacketTribulleOpcodes.Receive.ST_ExclureMembre:
                this.sendTribeMemberKick(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_OuvertureInterfaceTribu:
                this.sendOpenTribe(tribulleId, psData.readBoolean());
                break;
            case PacketTribulleOpcodes.Receive.ST_FermetureInterfaceTribu:
                this.sendCloseTribe(tribulleId);
                break;
            case PacketTribulleOpcodes.Receive.ST_ChangementDroitsRang:
                this.sendChangeTribeRankPermission(tribulleId, psData.readByte(), psData.readInt(), psData.readBoolean());
                break;
            case PacketTribulleOpcodes.Receive.ST_AffecterRang:
                this.sendChangeTribeMemberRank(tribulleId, psData.readUTF(), psData.readByte());
                break;
            case PacketTribulleOpcodes.Receive.ST_ChangementNomRang:
                this.sendChangeTribeRankName(tribulleId, psData.readByte(), psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_AjoutRang:
                this.sendCreateTribeRank(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_SupprimeRang:
                this.sendDeleteTribeRank(tribulleId, psData.readByte());
                break;
            case PacketTribulleOpcodes.Receive.ST_InverserRang:
                this.sendChangeTribeRankPosition(tribulleId, psData.readByte(), psData.readByte());
                break;
            case PacketTribulleOpcodes.Receive.ST_DesignerChef:
                this.sendChangeTribeLeader(tribulleId, psData.readUTF());
                break;
            case PacketTribulleOpcodes.Receive.ST_DemandeDissoudreTribu:
                this.sendDissolveTribe(tribulleId);
                break;
            case PacketTribulleOpcodes.Receive.ST_HistoriqueTribu:
                this.sendTribeHistory(tribulleId, psData.readInt(), psData.readInt());
                break;
            default:
                this.server.getLogger().debug(String.format("[PacketHandler] [%s] Unhandled tribulle packet: %s", player.getIpAddress(), tribulleCode));
                break;
        }
    }

    /**
     * Sends the platform connection to tribulle.
     */
    public void sendTribulleInitialization() {
        this.player.broadcastPacket(new PacketSendTribulleSwitchProtocol(true));
        if (this.player.getIsGuest())
            return;

        String playerName = this.player.getPlayerName();
        String tribeName = this.player.getPlayerData().tribeName;
        var players = this.server.getPlayers();
        this.player.broadcastPacket(new PacketSendEmailAddressVerified(this.player.getPlayerData().emailAddress, (this.server.getConfig().getSMTPConfig().getHost().isEmpty() || this.player.getPlayerData().isVerifiedEmailAddress)));
        NetByteArray packet = new NetByteArray();
        packet.writeByte(this.player.getPlayerData().playerGender.ordinal());
        packet.writeInt(this.player.getGlobalId());
        packet.writeBytes(this.buildFriendInfo(this.player.getPlayerData().soulmate, playerName).toByteArray());
        packet.writeShort((short) this.player.getPlayerData().getFriendList().size());
        for (String friendName : this.player.getPlayerData().getFriendList())
            packet.writeBytes(this.buildFriendInfo(friendName, playerName).toByteArray());

        packet.writeShort((short) this.player.getPlayerData().getIgnoredList().size());
        for (String ignoredName : this.player.getPlayerData().getIgnoredList())
            packet.writeUTF(ignoredName, true);

        packet.writeBytes(this.buildTribeConnectionInfo(tribeName, this.player.getPlayerData().tribeRank).toByteArray());
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatIdentificationService, packet);
        this.player.broadcastPacket(new PacketSendJoinChatChannel(this.player.getPlayerCommunity()));
        for (String friendName : this.player.getPlayerData().getFriendList()) {
            GamePlayer friend = players.get(friendName);
            if (friend != null && friend.getPlayerData().getFriendList().contains(playerName)) {
                friend.getParseTribulle().sendFriendModification(playerName, 1);
            }
        }

        if (!tribeName.isEmpty()) {
            TribeEntry tribe = this.server.getDatabase().loadTribeByName(tribeName);
            if (tribe == null)
                return;

            for (String memberName : tribe.getTribeMembers()) {
                GamePlayer member = players.get(memberName);
                if (member != null) {
                    member.getParseTribulle().sendTribeMemberModification(playerName, 1, member.isOpenTribe);
                }
            }
        }

        this.server.getChatMessages().put(this.player.getPlayerName(), new HashMap<>());
        this.server.getWhisperMessages().put(this.player.getPlayerName(), new HashMap<>());
        this.sendStaffLoginMessage(String.format("• [%s] $Connexion_Ami", this.player.getPlayerCommunity()));
        if (Utils.getUnixTime() - this.player.getPlayerData().lastConnectedTime >= 86400) {
            this.server.getDatabase().saveConnectionLog(new ConnectionLogEntry(this.player.getPlayerName(), this.player.getIpAddress(), Utils.getCountryNameByCode(this.player.getCountryCode()), this.player.getPlayerCommunity()));
        }
    }

    /**
     * Deinitializes the tribulle.
     */
    public void sendTribulleDestruction() {
        String playerName = this.player.getPlayerName();
        String tribeName = this.player.getPlayerData().tribeName;
        var players = this.server.getPlayers();
        for (String friendName : this.player.getPlayerData().getFriendList()) {
            GamePlayer friend = players.get(friendName);
            if (friend != null && friend.getPlayerData().getFriendList().contains(playerName)) {
                friend.getParseTribulle().sendFriendModification(playerName, 0);
            }
        }

        if (!tribeName.isEmpty()) {
            TribeEntry tribe = this.server.getDatabase().loadTribeByName(tribeName);
            if (tribe == null)
                return;

            for (String memberName : tribe.getTribeMembers()) {
                GamePlayer member = players.get(memberName);
                if (member != null) {
                    member.getParseTribulle().sendTribeMemberModification(playerName, 0, member.isOpenTribe);
                }
            }
        }

        this.server.getWhisperMessages().remove(playerName);
        this.server.getChatMessages().remove(playerName);
        this.sendStaffLoginMessage(String.format("• [%s] $Deconnexion_Ami", this.player.getPlayerCommunity()));
    }

    /**
     * Adds a friend in the friend list.
     * @param tribulleId The tribulle id.
     * @param friendName The friend name to add.
     */
    private void sendAddFriend(final long tribulleId, final String friendName) {
        if (friendName == null || friendName.equals(this.player.getPlayerName())) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutAmi, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(15));
            return;
        }

        if (this.player.getPlayerData().getFriendList().contains(friendName)) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutAmi, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        if (this.player.getPlayerData().getFriendList().size() > 250) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutAmi, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        if (this.server.getDatabase().loadPlayerByName(friendName) == null) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutAmi, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(12));
            return;
        }

        this.player.getPlayerData().getFriendList().add(friendName);
        NetByteArray data = new NetByteArray();
        data.writeBytes(this.buildFriendInfo(friendName, this.player.getPlayerName()).toByteArray());
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementAjoutAmi, data);
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutAmi, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        if (this.server.getPlayers().get(friendName) != null) {
            this.server.getPlayers().get(friendName).getParseTribulle().sendFriendModification(this.player.getPlayerName(), -1);
        }
    }

    /**
     * Adds a player in the block list.
     * @param tribulleId The tribulle id.
     * @param ignoredName The player's name to add.
     */
    private void sendAddIgnored(final long tribulleId, final String ignoredName) {
        var data = this.player.getPlayerData();
        var ignoredList = data.getIgnoredList();
        var friendList = data.getFriendList();
        String playerName = this.player.getPlayerName();
        if (ignoredName.equals(playerName) || ignoredName.equals(data.soulmate) || ignoredList.contains(ignoredName)) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutListeNoire, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        if (ignoredList.size() >= 250) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutListeNoire, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        PlayerEntity entity = this.server.getDatabase().loadPlayerByName(ignoredName);
        if (entity == null) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutListeNoire, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(12));
            return;
        }

        if (friendList.remove(ignoredName))
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementSuppressionAmi, (NetByteArray) new NetByteArray().writeInt(entity.getAccountId()));

        ignoredList.add(ignoredName);
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutListeNoire, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        var ignoredPlayer = this.server.getPlayers().get(ignoredName);
        if (ignoredPlayer != null && ignoredPlayer.getPlayerData().getFriendList().contains(playerName))
            ignoredPlayer.getParseTribulle().sendFriendModification(playerName, -1);
    }

    /**
     * Changes the player's gender.
     * @param tribulleId The tribulle id.
     * @param genderId The gender id.
     */
    private void sendChangePlayerGender(final long tribulleId, byte genderId) {
        this.player.getPlayerData().playerGender = (genderId == 0 ? PlayerGender.UNSPECIFIED : genderId == 1 ? PlayerGender.FEMALE : PlayerGender.MALE);
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementGenre, (NetByteArray) new NetByteArray().writeByte(genderId));
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatChangementGenre, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        for(String friendName : this.player.getPlayerData().getFriendList()) {
            GamePlayer friendClient = this.server.getPlayers().get(friendName);
            if (friendClient != null && friendClient.getPlayerData().getFriendList().contains(this.player.getPlayerName())) {
                if (friendClient.isOpenFriendList) {
                    friendClient.getParseTribulle().sendFriendList(-1, true);
                }
            }
        }

        if (!this.player.getPlayerData().tribeName.isEmpty()) {
            TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
            for(String member : myTribe.getTribeMembers()) {
                GamePlayer memberClient = this.server.getPlayers().get(member);
                if (memberClient != null) {
                    if (memberClient.isOpenTribe)
                        memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresMembre, this.buildTribeMemberInfo(this.player.getPlayerName()));
                }
            }
        }
    }

    /**
     * Changes the tribe house's map code.
     * @param mapCode The map code to change.
     */
    private void sendChangeTribeHouseMap(final int mapCode) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.CHANGE_TRIBE_HOUSE_MAP))
            return;

        MapEntry mapDetails = this.server.getDatabase().loadMapByCode(mapCode);
        if (mapDetails == null) {
            this.player.broadcastLegacyPacket(new PacketSendChangeTribeHouseResult(16));
            return;
        }

        if (mapDetails.mapCategory != 22) {
            this.player.broadcastLegacyPacket(new PacketSendChangeTribeHouseResult(17));
            return;
        }

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        myTribe.tribeHouseMap = mapCode;
        myTribe.getTribeHistory().add(new TribeEntry.TribeHistoricEntry(8, String.format("{\"auteur\":\"%s\",\"code\":\"%s\"}", this.player.getPlayerName(), mapCode)));
        this.server.getDatabase().saveTribe(myTribe);
        for (String member : myTribe.getTribeMembers()) {
            GamePlayer memberClient = this.server.getPlayers().get(member);
            if (memberClient != null && memberClient.isOpenTribe) {
                memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
            }
        }
    }

    /**
     * Changes the tribe leader.
     * @param tribulleId The tribulle id.
     * @param playerName The name of the new leader.
     */
    private void sendChangeTribeLeader(final long tribulleId, final String playerName) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.LEADER))
            return;

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        PlayerEntity playerAccount = (this.server.getPlayers().get(playerName) != null ? this.server.getPlayers().get(playerName).getPlayerData() : this.server.getDatabase().loadPlayerByName(playerName));
        playerAccount.tribeRank = myTribe.getTribeRanks().getLast();
        this.server.getDatabase().savePlayer(playerAccount);
        this.player.getPlayerData().tribeRank = myTribe.getTribeRanks().get(myTribe.getTribeRanks().size() - 2);
        for (String member : myTribe.getTribeMembers()) {
            GamePlayer memberClient = server.getPlayers().get(member);
            if (memberClient != null) {
                if (memberClient.isOpenTribe) {
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresMembre, this.buildTribeMemberInfo(playerName));
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresMembre, this.buildTribeMemberInfo(this.player.getPlayerName()));
                }
            }
        }

        myTribe.getTribeHistory().add(new TribeEntry.TribeHistoricEntry(5, String.format("{\"auteur\":\"%s\",\"cible\":\"%s\",\"rang\":\"%s\"}", this.player.getPlayerName(), playerName, playerAccount.tribeRank.name)));
        myTribe.getTribeHistory().add(new TribeEntry.TribeHistoricEntry(5, String.format("{\"auteur\":\"%s\",\"cible\":\"%s\",\"rang\":\"%s\"}", this.player.getPlayerName(), this.player.getPlayerName(), this.player.getPlayerData().tribeRank.name)));
        myTribe.tribeLeader = playerName;
        this.server.getDatabase().saveTribe(myTribe);
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDesignerChef, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the rank of given player in the tribe.
     * @param tribulleId The tribulle id.
     * @param playerName The player's name.
     * @param rankPos The rank position.
     */
    private void sendChangeTribeMemberRank(final long tribulleId, final String playerName, final int rankPos) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.CHANGE_MEMBERS_RANK))
            return;

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        if (myTribe.getTribeRanks().size() < rankPos)
            return;

        PlayerEntity myAccount = (this.server.getPlayers().get(playerName) != null ? this.server.getPlayers().get(playerName).getPlayerData() : this.server.getDatabase().loadPlayerByName(playerName));
        if (myAccount.tribeRank.position > rankPos)
            return;

        myAccount.tribeRank = myTribe.getTribeRanks().get(rankPos);
        myTribe.getTribeHistory().add(new TribeEntry.TribeHistoricEntry(5, String.format("{\"auteur\":\"%s\",\"cible\":\"%s\",\"rang\":\"%s\"}", this.player.getPlayerName(), playerName, myTribe.getTribeRanks().get(rankPos).name)));
        this.server.getDatabase().saveTribe(myTribe);
        this.server.getDatabase().savePlayer(myAccount);
        for (String member : myTribe.getTribeMembers()) {
            GamePlayer memberClient = this.server.getPlayers().get(member);
            if (memberClient != null) {
                memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementRang, new NetByteArray().writeUTF(this.player.getPlayerName(), true).writeUTF(playerName, true).writeUTF(myAccount.tribeRank.name, true));
                if (memberClient.isOpenTribe)
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresMembre, this.buildTribeMemberInfo(playerName));
            }
        }

        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAffecterRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the tribe's greeting message.
     * @param tribulleId The tribulle id.
     * @param message The message.
     */
    private void sendChangeTribeMessage(final long tribulleId, String message) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.EDIT_TRIBE_MESSAGE))
            return;

        message = this.server.formatText(message);
        if (message.length() > 500) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatChangerMessageJour, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(22));
            return;
        }

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        myTribe.tribeMessage = message;
        myTribe.getTribeHistory().add(new TribeEntry.TribeHistoricEntry(6, String.format("{\"auteur\":\"%s\"}", this.player.getPlayerName())));
        this.server.getDatabase().saveTribe(myTribe);
        for (String member : myTribe.getTribeMembers()) {
            GamePlayer memberClient = this.server.getPlayers().get(member);
            if (memberClient != null) {
                memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementMessageJour, new NetByteArray().writeUTF(this.player.getPlayerName(), true).writeUTF(message, true));
                if (memberClient.isOpenTribe) {
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
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
    private void sendChangeTribeRankName(final long tribulleId, final byte rankPos, String rankName) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        rankName = this.server.formatText(rankName);
        if (rankName.isEmpty() || !rankName.matches("^[ a-zA-Z0-9]*$")) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatChangementNomRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(8));
            return;
        }

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        String finalRankName = rankName;
        if (myTribe.getTribeRanks().stream().anyMatch(rank -> rank.name.equals(finalRankName))) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatChangementNomRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(9));
            return;
        }

        myTribe.getTribeRanks().get(rankPos).name = rankName;
        this.server.getDatabase().saveTribe(myTribe);
        for (String member : myTribe.getTribeMembers()) {
            GamePlayer memberClient = this.server.getPlayers().get(member);
            if (memberClient != null) {
                if (memberClient.isOpenTribe) {
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
                }
            }
        }

        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatChangementNomRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the rank permission.
     * @param tribulleId The tribulle id.
     * @param rankPos The rank position.
     * @param rankPerm The rank permission.
     * @param isDisabled Is disabled permission.
     */
    private void sendChangeTribeRankPermission(final long tribulleId, final int rankPos, final int rankPerm, final boolean isDisabled) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        myTribe.getTribeRanks().get(rankPos).setPerm(rankPerm - 1, !isDisabled);
        this.server.getDatabase().saveTribe(myTribe);
        for (String member : myTribe.getTribeMembers()) {
            GamePlayer memberClient = this.server.getPlayers().get(member);
            if (memberClient != null) {
                if (memberClient.isOpenTribe) {
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
                }
            }
        }

        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatChangementDroitsRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Changes the position of given tribe rank.
     * @param tribulleId The tribulle id.
     * @param prevPos The previous rank position.
     * @param nextPos The next rank position.
     */
    private void sendChangeTribeRankPosition(final long tribulleId, final int prevPos, final int nextPos) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        if (myTribe.getTribeRanks().size() < prevPos || myTribe.getTribeRanks().size() < nextPos)
            return;

        TribeEntry.TribeRank rank = myTribe.getTribeRanks().get(prevPos);
        rank.position = nextPos;
        TribeEntry.TribeRank rank2 = myTribe.getTribeRanks().get(nextPos);
        rank2.position = prevPos;
        myTribe.getTribeRanks().set(prevPos, rank2);
        myTribe.getTribeRanks().set(nextPos, rank);
        this.server.getDatabase().saveTribe(myTribe);
        for (String member : myTribe.getTribeMembers()) {
            GamePlayer memberClient = this.server.getPlayers().get(member);
            if (memberClient != null) {
                if (memberClient.isOpenTribe) {
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
                }
            }
        }

        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatInverserRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Closes the friend list.
     * @param tribulleId Tribulle id.
     */
    private void sendCloseFriendList(final long tribulleId) {
        this.player.isOpenFriendList = false;
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeFermetureListeAmis, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Closes the channel name.
     * @param tribulleId Tribulle id.
     * @param chatName The chat name.
     */
    private void sendCloseGlobalChannel(final long tribulleId, final String chatName) {
        if (this.server.getChats().containsKey(chatName)) {
            this.server.getChats().get(chatName).remove(this.player.getPlayerName());

            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementQuitterCanalPublique, new NetByteArray().writeUTF(chatName, true));
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatQuitterCanalPublique, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Closes the current tribe.
     * @param tribulleId The tribulle id.
     */
    private void sendCloseTribe(final long tribulleId) {
        if (this.player.getPlayerData().tribeName.isEmpty())
            return;

        this.player.isOpenTribe = false;
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatFermetureInterfaceTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Creates a new chat in the game.
     * @param tribulleId The tribulle id.
     * @param chatName The chat name.
     */
    private void sendCreateNewGlobalChannel(final long tribulleId, final String chatName) {
        if (!chatName.matches("^[a-zA-Z0-9_.-]*$")) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatRejoindreCanalPublique, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(8));
            return;
        }

        if (!this.server.getChats().containsKey(chatName)) {
            this.server.getChats().put(chatName, new ArrayList<>());
        }

        if (this.server.getChats().get(chatName).size() > 100) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatRejoindreCanalPublique, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        if (!this.server.getChats().get(chatName).contains(this.player.getPlayerName())) {
            this.server.getChats().get(chatName).add(this.player.getPlayerName());
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementRejoindreCanalPublique, new NetByteArray().writeUTF(chatName, true));
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatRejoindreCanalPublique, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Creates a new tribe.
     * @param tribulleId The tribulle id.
     * @param tribeName The tribe name.
     */
    private void sendCreateNewTribe(final long tribulleId, String tribeName) {
        if (!this.player.getPlayerData().tribeName.isEmpty()) {
            return;
        }

        tribeName = this.server.formatText(tribeName);
        if (tribeName.isEmpty() || !tribeName.matches("^[ a-zA-Z0-9]*$")) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeCreerTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(8));
            return;
        }

        if (this.server.getDatabase().loadTribeByName(tribeName) != null) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeCreerTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(9));
            return;
        }

        if (this.player.getPlayerData().shopCheese < 500) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeCreerTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(14));
            return;
        }

        this.player.getPlayerData().shopCheese -= 500;
        TribeEntry myTribe = new TribeEntry(this.server.getNextDatabaseCounterId("lastTribeId"), tribeName, this.player.getPlayerName());
        this.server.getDatabase().saveTribe(myTribe);
        this.player.getPlayerData().tribeName = tribeName;
        this.player.getPlayerData().tribeRank = myTribe.getTribeRanks().getLast();
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleInformationsMembreTribu, this.buildTribeConnectionInfo(this.player.getPlayerData().tribeName, this.player.getPlayerData().tribeRank));
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeCreerTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Creates a new tribe rank.
     * @param tribulleId The tribulle id.
     * @param rankName The rank name.
     */
    private void sendCreateTribeRank(final long tribulleId, String rankName) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        rankName = this.server.formatText(rankName);
        if (rankName.isEmpty() || !rankName.matches("^[ a-zA-Z0-9]*$")) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(8));
            return;
        }

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        var ranks = myTribe.getTribeRanks();
        String finalRankName = rankName;
        if (ranks.stream().anyMatch(rank -> rank.name.equals(finalRankName))) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(9));
            return;
        }

        if (ranks.size() >= 100) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(7));
            return;
        }

        for (String member : myTribe.getTribeMembers()) {
            var memberClient = this.server.getPlayers().get(member);
            PlayerEntity memberAccount = memberClient != null ? memberClient.getPlayerData() : this.server.getDatabase().loadPlayerByName(member);
            TribeEntry.TribeRank memberRank = memberAccount.tribeRank;
            int oldPosition = memberRank.position;
            if (oldPosition >= 1)
                memberRank.position = oldPosition + 1;

            this.server.getDatabase().savePlayer(memberAccount);
        }

        ranks.add(1, new TribeEntry.TribeRank(rankName, 1, 0));
        for (int i = 0; i < ranks.size(); i++)
            ranks.get(i).position = i;

        this.server.getDatabase().saveTribe(myTribe);
        for (String member : myTribe.getTribeMembers()) {
            var memberClient = this.server.getPlayers().get(member);
            if (memberClient != null && memberClient.isOpenTribe)
                memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
        }

        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatAjoutRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Deletes a tribe rank from the tribe.
     * @param tribulleId The tribulle id.
     * @param position The rank position.
     */
    private void sendDeleteTribeRank(final long tribulleId, final byte position) {
        if (!this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.EDIT_GLOBAL_RANKS))
            return;

        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        var ranks = myTribe.getTribeRanks();
        if (position == 0 || position >= ranks.size() - 1)
            return;

        TribeEntry.TribeRank prevRank = ranks.get(position);
        TribeEntry.TribeRank newRank = ranks.get(position + 1);
        int prevRankPos = prevRank.position;
        ranks.remove(position);
        for (int i = 0; i < ranks.size(); i++)
            ranks.get(i).position = i;

        for (String member : myTribe.getTribeMembers()) {
            var memberClient = this.server.getPlayers().get(member);
            PlayerEntity account = memberClient != null ? memberClient.getPlayerData() : this.server.getDatabase().loadPlayerByName(member);
            TribeEntry.TribeRank memberRank = account.tribeRank;
            String oldName = memberRank.name;
            int oldPosition = memberRank.position;
            if (oldPosition == prevRankPos) {
                memberRank = newRank;
            } else {
                for (TribeEntry.TribeRank rank : ranks) {
                    if (rank.name.equals(oldName)) {
                        memberRank = rank;
                        break;
                    }
                }
            }

            TribeEntry.TribeRank updatedRank = new TribeEntry.TribeRank(memberRank.name, memberRank.position, 0);
            for (int i = 0; i < 10; i++)
                updatedRank.setPerm(i, memberRank.hasPerm(TribeEntry.TribeRank.TribePerms.values()[i]));

            account.tribeRank = updatedRank;
            this.server.getDatabase().savePlayer(account);
        }

        this.server.getDatabase().saveTribe(myTribe);
        for (String member : myTribe.getTribeMembers()) {
            var memberClient = this.server.getPlayers().get(member);
            if (memberClient != null && memberClient.isOpenTribe)
                memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
        }

        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatSupprimeRang, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sets silence mode in current player.
     * @param tribulleId The tribulle id
     * @param silenceType The silence type.
     * @param silenceMessage The silence message.
     */
    private void sendDefineModeSilence(final long tribulleId, final byte silenceType, final String silenceMessage) {
        this.player.silenceType = silenceType;
        this.player.silenceMessage = silenceMessage;
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDefinirModeSilence, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Dissolves the current tribe.
     * @param tribulleId The tribulle id.
     */
    private void sendDissolveTribe(final long tribulleId) {
        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        if (myTribe != null && this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.LEADER)) {
            for (String member : myTribe.getTribeMembers()) {
                PlayerEntity myAccount = (this.server.getPlayers().get(member) != null ? this.server.getPlayers().get(member).getPlayerData() : this.server.getDatabase().loadPlayerByName(member));
                myAccount.tribeName = "";
                myAccount.tribeRank = new TribeEntry.TribeRank();
                this.server.getDatabase().savePlayer(myAccount);
                if (this.server.getPlayers().get(member) != null) {
                    this.server.getPlayers().get(member).getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleInformationsMembreTribu, this.buildTribeConnectionInfo("", null));
                }
            }

            this.server.getDatabase().deleteTribe(myTribe);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeDissoudreTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        } else {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeDissoudreTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(0));
        }
    }

    /**
     * Sends the friend list.
     */
    private void sendFriendList(final long tribulleId, boolean isOpen) {
        NetByteArray data = new NetByteArray();
        var friendList = this.player.getPlayerData().getFriendList();

        this.player.isOpenFriendList = true;
        data.writeBytes(this.buildFriendInfo(this.player.getPlayerData().soulmate, this.player.getPlayerName()).toByteArray());
        data.writeShort((short)friendList.size());
        for (String friendName : friendList)
            data.writeBytes(this.buildFriendInfo(friendName, this.player.getPlayerName()).toByteArray());

        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementListeAmis, data);
        if (!isOpen)
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeOuvertureListeAmis, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends a message in the given channel name.
     * @param tribulleId The tribulle id.
     * @param chatName The chat name.
     * @param message The message to send.
     */
    private void sendGlobalChannelMessage(final long tribulleId, final String chatName, String message) {
        if (this.player.getPlayerData().playedTime / 3600 < 4) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageChat, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(28));
            return;
        }

        message = this.server.formatText(message);
        if (message.length() > 90) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageChat, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(22));
            return;
        }

        SanctionEntry mySanction = this.server.getDatabase().loadLastSanction(player.getPlayerName(), SanctionEntry.SanctionType.SANCTION_MUTE);
        if (mySanction != null) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageChat, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(23));
            return;
        }

        if (this.player.getChatMessageTimer().getRemainingTime() <= 0) {
            var chatPlayers = this.server.getChats().get(chatName);
            if (chatPlayers != null && chatPlayers.contains(this.player.getPlayerName())) {
                String playerName = this.player.getPlayerName();
                for (String username : chatPlayers) {
                    var recipient = this.server.getPlayers().get(username);
                    if (recipient != null) {
                        NetByteArray packet = new NetByteArray();
                        packet.writeUTF(playerName, true);
                        packet.writeInt(GameCommunity.fromCode(this.player.getPlayerCommunity()).getId() + 1);
                        packet.writeUTF(chatName, true);
                        packet.writeUTF(message, true);
                        recipient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementMessageChat, packet);
                    }
                }

                this.player.getChatMessageTimer().schedule();
            }
        } else {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageChat, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(24));
        }
    }

    /**
     * Sends the member list in the given channel name.
     * @param tribulleId The tribulle id.
     * @param chatName The chat name.
     */
    private void sendGlobalChannelMembersList(final long tribulleId, final String chatName) {
        if (this.server.getChats().get(chatName) != null) {
            NetByteArray data = new NetByteArray();
            data.writeInt(tribulleId);
            data.writeByte(1);
            data.writeShort((short)this.server.getChats().get(chatName).size());
            for(String member : this.server.getChats().get(chatName))
                data.writeUTF(member, true);

            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatListerCanalPublique, data);
        }
    }

    /**
     * Answers to the marriage invitation
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     * @param answer The answer (True for yes, False for no).
     */
    private void sendMarriageAnswer(final long tribulleId, final String playerName, final boolean answer) {
        GamePlayer playerClient = this.server.getPlayers().get(playerName);
        this.player.currentMarriageInvite = "";
        if (playerClient == null) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatReponseDemandeEnMariage, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        if (!answer) {
            playerClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementRefusMariage, new NetByteArray().writeUTF(this.player.getPlayerName(), true));
        } else {
            this.player.getPlayerData().soulmate = playerName;
            playerClient.getPlayerData().soulmate = this.player.getPlayerName();
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementMariage, new NetByteArray().writeUTF(playerName, true));
            playerClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementMariage, new NetByteArray().writeUTF(this.player.getPlayerName(), true));
            if (this.player.isOpenFriendList)
                this.sendFriendList(-1, true);
            if (playerClient.isOpenFriendList)
                playerClient.getParseTribulle().sendFriendList(-1, true);

            this.server.getMarriageTimer(playerClient.getPlayerData().getUsername()).schedule();
        }

        playerClient.currentMarriageInvite = "";
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatReponseDemandeEnMariage, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Divorces the current marriage.
     * @param tribulleId The tribulle id,
     */
    private void sendMarriageDivorce(final long tribulleId) {
        PlayerEntity playerData = this.player.getPlayerData();
        String soulmateName = playerData.soulmate;
        if (soulmateName.isEmpty()) {
            return;
        }

        GamePlayer soulmatePlayer = this.server.getPlayers().get(soulmateName);
        PlayerEntity soulmateData = soulmatePlayer != null ? soulmatePlayer.getPlayerData() : this.server.getDatabase().loadPlayerByName(soulmateName);
        if (soulmateData == null)
            return;

        String playerName = this.player.getPlayerName();
        playerData.soulmate = "";
        soulmateData.soulmate = "";
        this.server.getDatabase().savePlayer(soulmateData);
        NetByteArray packet = new NetByteArray();
        packet.writeUTF(soulmateName, true);
        packet.writeBoolean(true);
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementDivorce, packet);
        if (soulmatePlayer != null) {
            NetByteArray soulmatePacket = new NetByteArray();
            soulmatePacket.writeUTF(playerName, true);
            soulmatePacket.writeBoolean(false);
            soulmatePlayer.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementDivorce, soulmatePacket);
            if (soulmatePlayer.isOpenFriendList)
                soulmatePlayer.getParseTribulle().sendFriendList(-1, true);
        }

        this.sendFriendList(-1, true);
        NetByteArray result = new NetByteArray();
        result.writeInt(tribulleId);
        result.writeByte(1);
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeDivorce, result);
    }

    /**
     * Sends marriage invitation to the given player.
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     */
    private void sendMarriageInvitation(final long tribulleId, final String playerName) {
        GamePlayer playerClient = this.server.getPlayers().get(playerName);
        if (playerClient == null || playerClient.getIsGuest()) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeEnMariage, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(11));
            return;
        }

        if (!playerClient.getPlayerData().soulmate.isEmpty()) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeEnMariage, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(15));
            return;
        }

        if (!this.player.currentMarriageInvite.isEmpty() || !playerClient.currentMarriageInvite.isEmpty()) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeEnMariage, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(6));
            return;
        }

        if (this.player.getPlayerData().getIgnoredList().contains(playerName)) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeEnMariage, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(4));
            return;
        }

        if (this.server.getMarriageTimer(this.player.getPlayerName()).getRemainingTime() > 0) {
            this.player.broadcastPacket(new PacketSendTranslatedMessage("", "$erreur.tribulle.30"));
            return;
        }

        this.player.currentMarriageInvite = playerName;
        playerClient.currentMarriageInvite = this.player.getPlayerName();
        playerClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementDemandeEnMariage, new NetByteArray().writeUTF(this.player.getPlayerName(), true));
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeEnMariage, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends the ignored list.
     */
    private void sendIgnoredList(final long tribulleId) {
        NetByteArray byteArray = new NetByteArray();
        byteArray.writeInt(tribulleId);
        byteArray.writeShort((short)this.player.getPlayerData().getIgnoredList().size());
        for(String playerName : this.player.getPlayerData().getIgnoredList())
            byteArray.writeUTF(playerName, true);

        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatConsultationListeNoire, byteArray);
    }

    /**
     * Opens the tribe.
     * @param tribulleId The tribulle id.
     */
    private void sendOpenTribe(final long tribulleId, final boolean isConnected) {
        if (this.player.getPlayerData().tribeName.isEmpty()) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatOuvertureInterfaceTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(17));
            return;
        }

        this.player.isOpenTribe = true;
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, isConnected));
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatOuvertureInterfaceTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
    }

    /**
     * Sends a private player message.
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     * @param message The message to send.
     */
    private void sendPlayerWhisperMessage(final long tribulleId, final String playerName, String message) {
        if (this.player.getIsGuest()) {
            this.player.broadcastPacket(new PacketSendTranslatedMessage("", "$Créer_Compte_Parler"));
            return;
        }

        message = this.server.formatText(message);
        GamePlayer playerClient = this.server.getPlayers().get(playerName);
        NetByteArray packet = new NetByteArray();
        if (playerClient == null || playerName.startsWith("*")) {
            packet.writeInt(tribulleId);
            packet.writeByte(12);
            packet.writeUTF("", true);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessagePrive, packet);
            return;
        }

        if (this.player.getPlayerData().getIgnoredList().contains(playerName)) {
            packet.writeInt(tribulleId);
            packet.writeByte(27);
            packet.writeUTF("", true);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessagePrive, packet);
            return;
        }

        if (this.player.getChatMessageTimer().getRemainingTime() > 0) {
            packet.writeInt(tribulleId);
            packet.writeByte(24);
            packet.writeUTF("", true);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessagePrive, packet);
            return;
        }

        SanctionEntry mySanction = this.server.getDatabase().loadLastSanction(player.getPlayerName(), SanctionEntry.SanctionType.SANCTION_MUTE);
        if (mySanction != null) {
            packet.writeInt(tribulleId);
            packet.writeByte(23);
            packet.writeUTF("", true);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessagePrive, packet);
            return;
        }

        if (message.length() > 90) {
            packet.writeInt(tribulleId);
            packet.writeByte(22);
            packet.writeUTF("", true);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessagePrive, packet);
            return;
        }

        if (this.player.getPlayerData().playedTime / 3600 < 5 && !playerClient.getPlayerData().getFriendList().contains(this.player.getPlayerName()) && this.player.getPlayerData().getStaffRoles().isEmpty()) {
            packet.writeInt(tribulleId);
            packet.writeByte(28);
            packet.writeUTF("", true);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessagePrive, packet);
            return;
        }

        if (this.player.getPlayerData().getStaffRoles().isEmpty() && !playerName.equals(this.player.getPlayerName()) && (playerClient.silenceType == 2 || ((playerClient.silenceType == 1 && !playerClient.getPlayerData().getFriendList().contains(this.player.getPlayerName()))))) {
            packet.writeInt(tribulleId);
            packet.writeByte(25);
            packet.writeUTF(playerClient.silenceMessage, true);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessagePrive, packet);
            return;
        }

        NetByteArray packet2 = new NetByteArray();
        packet2.writeUTF(this.player.getPlayerName(), true);
        packet2.writeInt(GameCommunity.fromCode(this.player.getPlayerCommunity()).getId() + 1);
        packet2.writeUTF(playerClient.getPlayerName(), true);
        packet2.writeUTF(message, true);
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementMessagePrive, packet2);
        if (!playerName.equals(this.player.getPlayerName()))
            playerClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementMessagePrive, packet2);

        // #ChatLog
        Deque<String[]> messages = new ArrayDeque<>(60);
        messages.add(new String[]{new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()), message});
        this.server.getWhisperMessages().get(this.player.getPlayerName()).put(playerName, messages);
        packet.writeInt(tribulleId);
        packet.writeByte(1);
        packet.writeUTF("", true);
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessagePrive, packet);
    }

    /**
     * Removes a friend from the friend list.
     * @param tribulleId The tribulle id.
     * @param friendName The friend's name.
     */
    private void sendRemoveFriend(final long tribulleId, final String friendName) {
        if (this.player.getPlayerData().getFriendList().contains(friendName)) {
            PlayerEntity friendAccount = (this.server.getPlayers().get(friendName) != null ? this.server.getPlayers().get(friendName).getPlayerData() : this.server.getDatabase().loadPlayerByName(friendName));
            this.player.getPlayerData().getFriendList().remove(friendName);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementSuppressionAmi, (NetByteArray) new NetByteArray().writeInt(friendAccount.getAccountId()));
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatSuppressionAmi, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
            if (this.server.getPlayers().get(friendName) != null) {
                this.server.getPlayers().get(friendName).getParseTribulle().sendFriendModification(this.player.getPlayerName(), -1);
            }
        }
    }

    /**
     * Removes a player from block list.
     * @param tribulleId The tribulle id.
     * @param ignoredPlayer The given player name.
     */
    private void sendRemoveIgnored(final long tribulleId, final String ignoredPlayer) {
        if (this.player.getPlayerData().getIgnoredList().contains(ignoredPlayer)) {
            this.player.getPlayerData().getIgnoredList().remove(ignoredPlayer);

            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatSuppressionListeNoire, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Sends the tribe history.
     * @param tribulleId The tribulle id.
     * @param startIndex The start index.
     * @param limit The limit.
     */
    private void sendTribeHistory(final long tribulleId, final int startIndex, final int limit) {
        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        if (myTribe != null) {
            NetByteArray historicData = new NetByteArray();
            int cnt = 0;
            List<TribeEntry.TribeHistoricEntry> reverseHistoric = new ArrayList<>(myTribe.getTribeHistory());
            Collections.reverse(reverseHistoric);
            for (int i = startIndex; i < Math.min(reverseHistoric.size(), startIndex + limit); i++) {
                TribeEntry.TribeHistoricEntry entry = reverseHistoric.get(i);
                historicData.writeInt(entry.getDate());
                historicData.writeInt(entry.getType());
                historicData.writeUTF(entry.getInformation(), true);
                cnt += 1;
            }

            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatHistoriqueTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeShort((short)cnt).writeBytes(historicData.toByteArray()).writeInt(reverseHistoric.size()));
        }
    }

    /**
     * Sends tribe invite to the given player.
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     */
    private void sendTribeMemberInvitation(final long tribulleId, final String playerName) {
        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        if (myTribe != null && this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.INVITE_MEMBERS)) {
            GamePlayer player = this.server.getPlayers().get(playerName);
            if (player == null || player.getIsGuest()) {
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(11));
                return;
            }

            if (myTribe.getTribeMembers().size() >= 2500) {
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(7));
                return;
            }

            if (!player.getPlayerData().tribeName.isEmpty()) {
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(18));
                return;
            }

            if (!player.currentTribeInvite.isEmpty()) {
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(6));
                return;
            }

            player.currentTribeInvite = this.player.getPlayerName();
            player.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleInvitationTribu, new NetByteArray().writeUTF(this.player.getPlayerName(), true).writeUTF(this.player.getPlayerData().tribeName, true));
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        } else {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(0));
        }
    }

    /**
     * Answers to the tribe invite.
     * @param tribulleId The tribulle id.
     * @param playerName The player name.
     * @param isAccepted Is he accepted?
     */
    private void sendTribeMemberInvitationAnswer(final long tribulleId, final String playerName, final boolean isAccepted) {
        if (playerName.equals(this.player.currentTribeInvite)) {
            GamePlayer playerClient = this.server.getPlayers().get(playerName);
            if (playerClient == null) {
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatRepondsInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(17));
                return;
            }

            if (!this.player.getPlayerData().tribeName.isEmpty()) {
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatRepondsInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(18));
                return;
            }

            TribeEntry myTribe = this.server.getDatabase().loadTribeByName(playerClient.getPlayerData().tribeName);
            if (myTribe.getTribeMembers().size() >= 2500) {
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatRepondsInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(7));
                return;
            }

            if (isAccepted) {
                myTribe.getTribeMembers().add(this.player.getPlayerName());
                myTribe.getTribeHistory().add(new TribeEntry.TribeHistoricEntry(2, String.format("{\"auteur\":\"%s\",\"membreAjoute\":\"%s\"}", playerName, this.player.getPlayerName())));
                this.player.getPlayerData().tribeName = myTribe.getTribeName();
                this.player.getPlayerData().tribeRank = myTribe.getTribeRanks().getFirst();
                this.server.getDatabase().saveTribe(myTribe);
                for (String member : myTribe.getTribeMembers()) {
                    GamePlayer memberClient = this.server.getPlayers().get(member);
                    if (memberClient != null) {
                        memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleNouveauMembre, new NetByteArray().writeUTF(this.player.getPlayerName(), true));
                        if (memberClient.isOpenTribe)
                            memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
                    }
                }
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleInformationsMembreTribu, this.buildTribeConnectionInfo(this.player.getPlayerData().tribeName, this.player.getPlayerData().tribeRank));
            }

            this.player.currentTribeInvite = "";
            playerClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleReponseInvitationTribu, (NetByteArray) new NetByteArray().writeUTF(this.player.getPlayerName(), true).writeBoolean(isAccepted));
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatRepondsInvitationTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        }
    }

    /**
     * Sends when somebody gets kicked from the tribe.
     * @param tribulleId The tribulle id.
     * @param playerName The player name to kick.
     */
    private void sendTribeMemberKick(final long tribulleId, final String playerName) {
        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        if (myTribe != null && this.player.getPlayerData().tribeRank.hasPerm(TribeEntry.TribeRank.TribePerms.EXCLUDE_MEMBERS)) {
            if (this.server.getPlayers().get(playerName) != null) {
                this.server.getPlayers().get(playerName).isOpenTribe = false;
                this.server.getPlayers().get(playerName).getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleExclusionMembre, new NetByteArray().writeUTF(playerName, true).writeUTF(this.player.getPlayerName(), true));
            }

            myTribe.getTribeMembers().remove(playerName);
            myTribe.getTribeHistory().add(new TribeEntry.TribeHistoricEntry(3, String.format("{\"auteur\":\"%s\",\"membreExclu\":\"%s\"}", this.player.getPlayerName(), playerName)));
            for (String member : myTribe.getTribeMembers()) {
                GamePlayer memberClient = this.server.getPlayers().get(member);
                if (memberClient != null) {
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleExclusionMembre, new NetByteArray().writeUTF(playerName, true).writeUTF(this.player.getPlayerName(), true));
                    if (memberClient.isOpenTribe) {
                        memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
                    }
                }
            }

            PlayerEntity myAccount = this.server.getDatabase().loadPlayerByName(playerName);
            myAccount.tribeName = "";
            myAccount.tribeRank = new TribeEntry.TribeRank();
            this.server.getDatabase().savePlayer(myAccount);
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatExclureMembre, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        } else {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatExclureMembre, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(0));
        }
    }

    /**
     * Sends when member leave the current tribe.
     * @param tribulleId The tribulle id.
     */
    private void sendTribeMemberLeave(final long tribulleId) {
        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        if (myTribe != null) {
            if (this.player.getPlayerData().tribeRank == myTribe.getTribeRanks().getLast()) {
                this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeQuitterTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(4));
                return;
            }

            myTribe.getTribeMembers().remove(this.player.getPlayerName());
            myTribe.getTribeHistory().add(new TribeEntry.TribeHistoricEntry(4, String.format("{\"membreParti\":\"%s\"}", this.player.getPlayerName())));
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleDepartMembre, new NetByteArray().writeUTF(this.player.getPlayerName(), true));
            this.server.getDatabase().saveTribe(myTribe);
            for (String member : myTribe.getTribeMembers()) {
                GamePlayer memberClient = this.server.getPlayers().get(member);
                if (memberClient != null) {
                    memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleDepartMembre, new NetByteArray().writeUTF(this.player.getPlayerName(), true));
                    if (memberClient.isOpenTribe) {
                        memberClient.getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
                    }
                }
            }

            this.player.getPlayerData().tribeName = "";
            this.player.getPlayerData().tribeRank = new TribeEntry.TribeRank();
            this.player.isOpenTribe = false;
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeQuitterTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
        } else {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatDemandeQuitterTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(0));
        }
    }

    /**
     * Sends a message in #Tribe chat.
     * @param tribulleId The tribulle id.
     * @param message The message.
     */
    private void sendTribeMessage(final long tribulleId, String message) {
        TribeEntry myTribe = this.server.getDatabase().loadTribeByName(this.player.getPlayerData().tribeName);
        if (myTribe == null) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(17));
            return;
        }

        message = this.server.formatText(message);
        if (message.length() > 255) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(22));
            return;
        }

        SanctionEntry mySanction = this.server.getDatabase().loadLastSanction(player.getPlayerName(), SanctionEntry.SanctionType.SANCTION_MUTE);
        if (mySanction != null) {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(23));
            return;
        }

        if (this.player.getChatMessageTimer().getRemainingTime() <= 0) {
            for (String member : myTribe.getTribeMembers()) {
                if (this.server.getPlayers().get(member) != null) {
                    this.server.getPlayers().get(member).getParseTribulle().broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementMessageTribu, new NetByteArray().writeUTF(this.player.getPlayerName(), true).writeUTF(message, true));
                }
            }

            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(1));
            this.player.getChatMessageTimer().schedule();
        } else {
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_ResultatEnvoiMessageTribu, (NetByteArray) new NetByteArray().writeInt(tribulleId).writeByte(24));
        }
    }

    /**
     * Helper function for build the friend info.
     * @param friendName The friend name.
     * @param playerName The player name.
     * @return A packet that contains info about player's friend.
     */
    private NetByteArray buildFriendInfo(String friendName, String playerName) {
        NetByteArray packet = new NetByteArray();
        if (friendName.isEmpty()) {
            packet.writeInt(0);
            packet.writeUTF("", true);
            packet.writeByte(0);
            packet.writeInt(0);
            packet.writeBoolean(false);
            packet.writeBoolean(false);
            packet.writeInt(1);
            packet.writeUTF("", true);
            packet.writeInt(0);
            return packet;
        }

        var onlinePlayer = this.server.getPlayers().get(friendName);
        boolean online = onlinePlayer != null;
        PlayerEntity friendAccount;
        String roomName = "";
        if (online) {
            friendAccount = onlinePlayer.getPlayerData();
            roomName = onlinePlayer.getRoomName();
            String roomSuffix = roomName.substring(roomName.indexOf('-') + 1);
            if (roomSuffix.startsWith("@"))
                roomName = "";

            if (roomSuffix.startsWith("*"))
                roomName = roomSuffix;
        } else {
            friendAccount = this.server.getDatabase().loadPlayerByName(friendName);
        }

        packet.writeInt(friendAccount.getAccountId());
        packet.writeUTF(friendAccount.getUsername(), true);
        packet.writeByte(friendAccount.playerGender.ordinal());
        packet.writeInt(friendAccount.getAccountId());
        packet.writeBoolean(friendAccount.getFriendList().contains(playerName));
        packet.writeBoolean(online);
        packet.writeInt(online ? 4 : 0);
        packet.writeUTF(roomName, true);
        packet.writeInt(friendAccount.lastConnectedTime);
        return packet;
    }

    /**
     * Helper function for build the connection info of a tribe member.
     * @param tribeName The tribe name.
     * @param tribeRank The tribe rank.
     * @return A packet that contains connection info about tribe member when he connects to the game.
     */
    private NetByteArray buildTribeConnectionInfo(String tribeName, TribeEntry.TribeRank tribeRank) {
        NetByteArray packet = new NetByteArray();
        if (tribeName.isEmpty()) {
            packet.writeUTF("", true);
            packet.writeInt(0);
            packet.writeUTF("", true);
            packet.writeInt(0);
            packet.writeUTF("", true);
            packet.writeInt(0);
            return packet;
        }

        TribeEntry tribe = this.server.getDatabase().loadTribeByName(tribeName);
        if (tribe == null) {
            packet.writeUTF("", true);
            packet.writeInt(0);
            packet.writeUTF("", true);
            packet.writeInt(0);
            packet.writeUTF("", true);
            packet.writeInt(0);
            return packet;
        }

        packet.writeUTF(tribe.getTribeName(), true);
        packet.writeInt(tribe.getTribeId());
        packet.writeUTF(tribe.tribeMessage, true);
        packet.writeInt(tribe.tribeHouseMap);
        packet.writeUTF(tribeRank.name, true);
        packet.writeInt(tribeRank.getPerms());
        return packet;
    }

    /**
     * Helper function for build the tribe member info.
     * @param playerName The tribe member.
     * @return A packet that contains information about the player in the tribe.
     */
    private NetByteArray buildTribeMemberInfo(String playerName) {
        GamePlayer playerClient = this.server.getPlayers().get(playerName);
        PlayerEntity accountClient = playerClient != null ? playerClient.getPlayerData() : this.server.getDatabase().loadPlayerByName(playerName);
        boolean online = playerClient != null;
        String roomName = "";
        if (online) {
            roomName = playerClient.getRoomName();
            String roomSuffix = roomName.substring(roomName.indexOf('-') + 1);
            if (roomSuffix.startsWith("@"))
                roomName = "";

            if (roomSuffix.startsWith("*"))
                roomName = roomSuffix;
        }

        NetByteArray data = new NetByteArray();
        data.writeInt(accountClient.getAccountId());
        data.writeUTF(accountClient.getUsername(), true);
        data.writeByte(accountClient.playerGender.ordinal());
        data.writeInt(accountClient.getAccountId());
        data.writeInt(online ? 0 : accountClient.lastConnectedTime);
        data.writeByte(accountClient.tribeRank.position);
        data.writeInt(online ? 4 : 1);
        data.writeUTF(roomName, true);
        return data;
    }

    /**
     * Helper function for build the info of all tribe members.
     * @param tribeName The tribe name.
     * @param isDisconnected Show only disconnected members.
     * @return A packet that contains information about all tribe members.
     */
    private NetByteArray buildTribeMembersInfo(String tribeName, boolean isDisconnected) {
        NetByteArray packet = new NetByteArray();
        if (tribeName.isEmpty()) {
            packet.writeInt(0);
            packet.writeUTF("", true);
            packet.writeUTF("", true);
            packet.writeInt(0);
            packet.writeShort((short) 0);
            packet.writeShort((short) 0);
            return packet;
        }

        TribeEntry tribe = this.server.getDatabase().loadTribeByName(tribeName);
        if (tribe == null) {
            packet.writeInt(0);
            packet.writeUTF("", true);
            packet.writeUTF("", true);
            packet.writeInt(0);
            packet.writeShort((short) 0);
            packet.writeShort((short) 0);
            return packet;
        }

        var players = this.server.getPlayers();
        packet.writeInt(tribe.getTribeId());
        packet.writeUTF(tribe.getTribeName(), true);
        packet.writeUTF(tribe.tribeMessage, true);
        packet.writeInt(tribe.tribeHouseMap);
        short count = 0;
        NetByteArray members = new NetByteArray();
        for (String member : tribe.getTribeMembers()) {
            boolean online = players.containsKey(member);
            if (online == isDisconnected)
                continue;

            count++;
            members.writeBytes(this.buildTribeMemberInfo(member).toByteArray());
        }

        packet.writeShort(count);
        packet.writeBytes(members.toByteArray());
        packet.writeShort((short) tribe.getTribeRanks().size());
        for (var rank : tribe.getTribeRanks()) {
            packet.writeUTF(rank.name, true);
            packet.writeInt(rank.getPerms());
        }

        return packet;
    }

    /**
     * Sends a modification in friendship.
     * @param friendName The friend name.
     * @param type (Is online or offline for send message or -1 for not send the connection message).
     */
    public void sendFriendModification(String friendName, int type) {
        this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignalementModificationAmi, this.buildFriendInfo(friendName, this.player.getPlayerName()));
        int opcode = switch (type) {
            case 0 -> PacketTribulleOpcodes.Send.ET_SignalementDeconnexionAmi;
            case 1 -> PacketTribulleOpcodes.Send.ET_SignalementConnexionAmi;
            default -> -1;
        };

        if (opcode != -1)
            this.broadcastTribullePacket(opcode, new NetByteArray().writeUTF(friendName, true));
    }

    /**
     * Sends a notification when tribe member connects & disconnects from the game.
     * @param tribeMember The tribe member.
     * @param type Is online or offline?
     * @param isOpenTribe Is the tribe open to update.
     */
    public void sendTribeMemberModification(String tribeMember, int type, boolean isOpenTribe) {
        int opcode = switch (type) {
            case 1 -> PacketTribulleOpcodes.Send.ET_SignaleConnexionMembre;
            case 0 -> PacketTribulleOpcodes.Send.ET_SignaleDeconnexionMembre;
            default -> -1;
        };

        if (opcode != -1)
            this.broadcastTribullePacket(opcode, new NetByteArray().writeUTF(tribeMember, true));

        if (isOpenTribe)
            this.broadcastTribullePacket(PacketTribulleOpcodes.Send.ET_SignaleChangementParametresTribu, this.buildTribeMembersInfo(this.player.getPlayerData().tribeName, false));
    }

    /**
     * Sends a staff connection/disconnection message.
     * @param message The message's type.
     */
    private void sendStaffLoginMessage(String message) {
        if (this.player.getPlayerData().hasPublicAuthorization)
            return;

        StaffRoleType staffRole = null;
        int channelId;
        if (this.player.hasStaffPermission(StaffRoleType.TRIAL_MODO, true) || this.player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, true) || this.player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, true)) {
            channelId = 4;
        } else if (this.player.hasStaffPermission(StaffRoleType.ARBITRE, true)) {
            staffRole = StaffRoleType.ARBITRE;
            channelId = 2;
        } else if (this.player.hasStaffPermission(StaffRoleType.MAPCREW, true)) {
            staffRole = StaffRoleType.MAPCREW;
            channelId = 7;
        } else if (this.player.hasStaffPermission(StaffRoleType.FASHIONSQUAD, true)) {
            staffRole = StaffRoleType.FASHIONSQUAD;
            channelId = 10;
        } else if (this.player.hasStaffPermission(StaffRoleType.LUADEV, true)) {
            staffRole = StaffRoleType.LUADEV;
            channelId = 8;
        } else if (this.player.hasStaffPermission(StaffRoleType.FUNCORP, true)) {
            staffRole = StaffRoleType.FUNCORP;
            channelId = 9;
        } else {
            return;
        }

        List<String[]> staffInfo = new ArrayList<>();
        List<String> r1 = List.of(this.player.getPlayerName());
        for (GamePlayer client : this.server.getPlayers().values()) {
            boolean isSameTeam = client.hasStaffPermission(staffRole, true);
            if (!isSameTeam)
                continue;

            if (client.getPlayerData().hasPublicAuthorization)
                continue;

            client.broadcastPacket(new PacketSendStaffChatMessage(channelId, this.player.getPlayerName(), message, r1, true));
            staffInfo.add(new String[]{client.getPlayerCommunity(), client.getPlayerName(), client.getRoomName()});
        }

        if (message.contains("$Connexion_Ami")) {
            NetByteArray packet = new NetByteArray();
            packet.writeUnsignedByte(channelId);
            packet.writeUnsignedByte(staffInfo.size());
            for (String[] info : staffInfo) {
                packet.writeUTF(info[0], true);
                packet.writeUTF(info[1], true);
                packet.writeUTF(info[2], true);
            }

            packet.writeBoolean(true);
            this.player.broadcastPacket(new PSSendPacket(PacketOpcodes.SEND_STAFF_LIST) {
                @Override
                public PSByteArray build() throws PSSendPacketException {
                    return packet;
                }
            });
        }
    }
}