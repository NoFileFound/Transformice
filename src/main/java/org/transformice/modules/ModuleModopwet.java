package org.transformice.modules;

// Imports
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.psframework.network.PSSendPacket;
import org.psframework.utils.PSByteArray;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.LanguageInfo;
import org.transformice.data.objects.ReportType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.PlayerEntity;
import org.transformice.database.data.ReportEntry;
import org.transformice.database.data.SanctionEntry;
import org.transformice.modules.impl.ModuleServer;
import org.transformice.network.NetByteArray;
import org.transformice.packets.PacketOpcodes;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.chat.PacketSendServerMessage;
import org.transformice.packets.send.modopwet.PacketSendBanConsideration;
import org.transformice.packets.send.modopwet.PacketSendModopwetChatLog;
import org.transformice.packets.send.modopwet.PacketSendModopwetCommunitiesList;
import org.transformice.packets.send.modopwet.PacketSendModopwetCommunitiesReportsCount;
import org.transformice.packets.send.modopwet.PacketSendModopwetPlayerBanned;
import org.transformice.packets.send.modopwet.PacketSendModopwetPlayerDisconnected;
import org.transformice.packets.send.modopwet.PacketSendModopwetReportStatusDeleted;
import org.transformice.packets.send.modopwet.PacketSendModopwetRoomPasswordProtected;
import org.transformice.packets.send.modopwet.PacketSendPlayerWatch;

public final class ModuleModopwet extends ModuleServer {
    private final List<ReportEntry> reports;
    private final Map<String, List<ReportEntry>> reportsByPlayer;
    private final Map<String, ReportEntry> latestReportByPlayer;
    private final ScheduledFuture<?> clearDeletedReportsTimer;

    /**
     * Creates a modopwet module.
     *
     * @param server The game server.
     */
    public ModuleModopwet(GameServer server) {
        super(server);
        this.reports = new ArrayList<>();
        this.reportsByPlayer = new HashMap<>();
        this.latestReportByPlayer = new ConcurrentHashMap<>();
        this.clearDeletedReportsTimer = this.server.getScheduler().scheduleAtFixedRate(this::clearDeletedReports, 24, 24, TimeUnit.HOURS);
    }

    /**
     * Clears the resolved reports after 24 hours.
     */
    public synchronized void clearDeletedReports() {
        int count = 0;
        for (ReportEntry report : new ArrayList<>(this.reports)) {
            if (!report.isResolved)
                continue;

            this.server.getDatabase().deleteReport(report);
            this.reports.remove(report);
            List<ReportEntry> playerReports = this.reportsByPlayer.get(report.getPlayerName());
            if (playerReports != null) {
                playerReports.remove(report);
                if (playerReports.isEmpty()) {
                    this.reportsByPlayer.remove(report.getPlayerName());
                    this.latestReportByPlayer.remove(report.getPlayerName(), report);
                } else if (this.latestReportByPlayer.get(report.getPlayerName()) == report) {
                    playerReports.stream().max(Comparator.comparingLong(ReportEntry::getReportCreationDate)).ifPresent(latest -> this.latestReportByPlayer.put(report.getPlayerName(), latest));
                }
            } else {
                this.latestReportByPlayer.remove(report.getPlayerName(), report);
            }

            count++;
        }

        this.server.getLogger().info(String.format("[Modopwet] Cleaned total %d resolved reports.", count));
    }

    /**
     * Loads the database.
     */
    public void loadDatabase() {
        this.reports.clear();
        this.reportsByPlayer.clear();
        this.latestReportByPlayer.clear();
        for (ReportEntry report : this.server.getDatabase().loadReports()) {
            this.reports.add(report);
            this.reportsByPlayer.computeIfAbsent(report.getPlayerName(), _ -> new ArrayList<>()).add(report);
            ReportEntry latestReport = this.latestReportByPlayer.get(report.getPlayerName());
            if (latestReport == null || report.getReportCreationDate() >= latestReport.getReportCreationDate()) {
                this.latestReportByPlayer.put(report.getPlayerName(), report);
            }
        }

        this.server.getLogger().info("[Modopwet] Loaded the modopwet database successfully.");
        this.clearDeletedReports();
    }

    /**
     * Saves the modopwet in the database.
     */
    public void saveDatabase() {
        for (ReportEntry report : this.reports)
            this.server.getDatabase().saveReport(report);

        this.server.getLogger().info("[Modopwet] Successfully saved the database.");
        this.clearDeletedReportsTimer.cancel(false);
    }

    /**
     * Handles when a new game report was made.
     * @param reporterPlayer The reporter's instance.
     * @param playerName The reported's player name.
     * @param reportType The report's type.
     * @param reportComment The report's comment.
     */
    public void sendCreateGameReport(GamePlayer reporterPlayer, String playerName, ReportType reportType, String reportComment) {
        if (reporterPlayer.getPlayerName().equals(playerName))
            return;

        GamePlayer reportedPlayer = this.server.getPlayers().get(playerName);
        if (reportedPlayer == null) {
            reporterPlayer.broadcastLegacyPacket(new PacketSendBanConsideration(""));
            return;
        }

        reporterPlayer.broadcastLegacyPacket(new PacketSendBanConsideration(reportedPlayer.getPlayerName()));
        ReportEntry.ReporterEntry reporterEntry = new ReportEntry.ReporterEntry(reporterPlayer.getPlayerName(), reportComment, reportType, reporterPlayer.getPlayerData().playerKarma);
        ReportEntry myReport = this.latestReportByPlayer.get(reportedPlayer.getPlayerName());
        if (myReport != null) {
            if (myReport.isResolved) {
                myReport.isResolved = false;
                myReport.resolvedBy = "";
                myReport.playerConnectionStatus = ReportEntry.PlayerConnectionStatus.CONNECTED;
                myReport.playerCommunity = reportedPlayer.getPlayerCommunity().toUpperCase();
                myReport.playedTime = reportedPlayer.getPlayerData().playedTime;
            }

            boolean state = myReport.getReporters().stream().anyMatch(reporter -> reporter.getPlayerName().equals(reporterPlayer.getPlayerName()));
            if (!state)
                myReport.getReporters().add(reporterEntry);
        } else {
            ReportEntry report = new ReportEntry(this.server.getNextDatabaseCounterId("lastReportId"), reportedPlayer.getPlayerName(), reportedPlayer.getPlayerCommunity().toUpperCase(), reportedPlayer.getPlayerData().playedTime, reportType, reporterEntry);
            this.reports.add(report);
            this.reportsByPlayer.computeIfAbsent(reportedPlayer.getPlayerName(), _ -> new ArrayList<>()).add(report);
            this.latestReportByPlayer.put(reportedPlayer.getPlayerName(), report);
        }

        this.sendModopwetMessage(String.format("<ROSE>[Modopwet]</ROSE> A new report has been submitted against <N>%s</N>.", reportedPlayer.getPlayerName()), reportedPlayer.getPlayerCommunity(), Set.of());
    }

    /**
     * Handles when the player is going to be banned.
     * @param moderatorPlayer The moderator player's instance.
     * @param playerName The player's name to ban.
     * @param isSilent Is the ban going to be silent in the room.
     */
    public void handleBanHack(GamePlayer moderatorPlayer, String playerName, boolean isSilent) {
        boolean result = this.server.sendPlayerBan(playerName, 360, "$MessageTriche", moderatorPlayer, isSilent);
        if (result) {
            ReportEntry myReport = this.latestReportByPlayer.get(playerName);
            if (myReport != null) {
                myReport.isResolved = true;
                myReport.resolvedBy = moderatorPlayer.getPlayerName();
                for (ReportEntry.ReporterEntry reporter : myReport.getReporters()) {
                    String reporterName = reporter.playerName;
                    GamePlayer reporterPlayer = this.server.getPlayers().get(reporterName);
                    PlayerEntity reporterEntity = reporterPlayer != null ? reporterPlayer.getPlayerData() : this.server.getDatabase().loadPlayerByName(reporterName);
                    reporterEntity.playerKarma++;
                    if (!reporterName.startsWith("*"))
                        this.server.getDatabase().savePlayer(reporterEntity);

                    if (reporterPlayer != null) {
                        reporterPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("$Traitement_Signalement (Karma %d)", reporterEntity.playerKarma), new ArrayList<>(List.of(playerName))));
                    }
                }
            }
        }

        this.sendOpenModopwet(moderatorPlayer, moderatorPlayer.isOpenModopwet);
    }

    /**
     * Handles when the player subscribes to a community.
     * @param moderatorPlayer The moderator's player instance.
     * @param isSubscribed Is the player subscribed.
     * @param modoCommunities The subscribed communities.
     */
    public void handleChatNotification(GamePlayer moderatorPlayer, boolean isSubscribed, List<String> modoCommunities) {
        moderatorPlayer.isModopwetSubscribed = isSubscribed;
        moderatorPlayer.modopwetSubscribedCommunities = modoCommunities;
    }

    /**
     * Handles when the moderator deletes a report from modopwet.
     * @param moderatorPlayer The moderator player's instance.
     * @param playerName The reported's player name.
     * @param isHandled Is the report handled or deleted.
     */
    public void handleDeleteReport(GamePlayer moderatorPlayer, String playerName, boolean isHandled) {
        GamePlayer reportedPlayer = this.server.getPlayers().get(playerName);
        ReportEntry report = this.latestReportByPlayer.get(reportedPlayer.getPlayerName());
        if (report == null || report.isResolved)
            return;

        report.isResolved = true;
        report.resolvedBy = moderatorPlayer.getPlayerName();
        if (isHandled) {
            for (ReportEntry.ReporterEntry reporter : report.getReporters()) {
                String reporterName = reporter.playerName;
                GamePlayer reporterPlayer = this.server.getPlayers().get(reporterName);
                PlayerEntity reporterEntity = reporterPlayer != null ? reporterPlayer.getPlayerData() : this.server.getDatabase().loadPlayerByName(reporterName);
                reporterEntity.playerKarma++;
                if (!reporterName.startsWith("*"))
                    this.server.getDatabase().savePlayer(reporterEntity);

                if (reporterPlayer != null) {
                    reporterPlayer.broadcastPacket(new PacketSendServerMessage(true, String.format("$Traitement_Signalement (Karma %d)", reporterEntity.playerKarma), new ArrayList<>(List.of(reportedPlayer.getPlayerName()))));
                }
            }
        }

        this.sendOpenModopwet(moderatorPlayer, moderatorPlayer.isOpenModopwet);
        for (GamePlayer watcher : reportedPlayer.getModopwetWatchers()) {
            if (watcher != null && watcher != moderatorPlayer) {
                watcher.sendEnterRoom(this.server.getRecommendedRoom("", watcher.getPlayerCommunity()), "");
                watcher.broadcastPacket(new PacketSendServerMessage(true, String.format("<ROSE>[Modopwet]</ROSE> The report against <BV>%s</BV> has been marked as resolved by <N>%s</N>.", playerName, moderatorPlayer.getPlayerName())));
            }
        }

        reportedPlayer.getModopwetWatchers().clear();
    }

    /**
     * Handles when the moderator open's the reported player's whisper logs.
     * @param moderatorPlayer The moderator player's instance.
     * @param playerName The reported's player name.
     */
    public void handleOpenChatLog(GamePlayer moderatorPlayer, String playerName) {
        GamePlayer reportedPlayer = this.server.getPlayers().get(playerName);
        if (reportedPlayer == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "The supplied argument isn't a valid nickname."));
            return;
        }

        var chatMessages = this.server.getChatMessages().get(reportedPlayer.getPlayerName());
        var whisperMessages = this.server.getWhisperMessages().get(reportedPlayer.getPlayerName());
        moderatorPlayer.broadcastPacket(new PacketSendModopwetChatLog(reportedPlayer.getPlayerName(), chatMessages, whisperMessages));
    }

    /**
     * Handles when the player opens the modopwet.
     * @param moderatorPlayer The moderator's player instance.
     * @param isOpen Is the modopwet open.
     */
    public void sendOpenModopwet(GamePlayer moderatorPlayer, boolean isOpen) {
        this.sendOpenModopwet(moderatorPlayer, isOpen, moderatorPlayer.modopwetCurrentCommunity, moderatorPlayer.isModopwetByOnePersonReports, moderatorPlayer.isModopwetSortedByTime, moderatorPlayer.isModopwetForwardSorted);
    }

    /**
     * Handles when the player opens the modopwet.
     * @param moderatorPlayer The moderator's player instance.
     * @param isOpen Is the modopwet opened.
     * @param modoCommunity The moderator's current modopwet community.
     * @param hasOnePersonReports Is the one person per report option enabled.
     * @param isSortedByTime Are the reports sorted by time.
     * @param isForwardSorted Are the reports forward sorted.
     */
    public void sendOpenModopwet(GamePlayer moderatorPlayer, boolean isOpen, String modoCommunity, boolean hasOnePersonReports, boolean isSortedByTime, boolean isForwardSorted) {
        moderatorPlayer.isOpenModopwet = isOpen;
        if (isOpen) {
            moderatorPlayer.modopwetCurrentCommunity = modoCommunity;
            moderatorPlayer.isModopwetSortedByTime = isSortedByTime;
            moderatorPlayer.isModopwetByOnePersonReports = hasOnePersonReports;
            moderatorPlayer.isModopwetForwardSorted = isForwardSorted;
            List<ReportEntry> info = new ArrayList<>(moderatorPlayer.isModopwetByOnePersonReports ? this.latestReportByPlayer.values() : this.reports);
            Set<String> disconnectedPlayers = new LinkedHashSet<>();
            Map<String, SanctionEntry> bannedPlayers = new LinkedHashMap<>();
            List<Map.Entry<String, String>> deletedReports = new ArrayList<>();
            Comparator<ReportEntry> cmp = moderatorPlayer.isModopwetSortedByTime ? Comparator.comparingLong(ReportEntry::getReportCreationDate) : Comparator.comparing(ReportEntry::getPlayerName, String.CASE_INSENSITIVE_ORDER);
            if (!modoCommunity.equals("ALL"))
                info.removeIf(report -> !report.playerCommunity.toUpperCase().equals(modoCommunity));

            cmp = cmp.thenComparingLong(ReportEntry::getId);
            info.sort(moderatorPlayer.isModopwetForwardSorted ? cmp : cmp.reversed());
            List<GameReport> gameReports = new ArrayList<>(info.size());
            int cnt = 0;
            for (ReportEntry report : info) {
                if (cnt > 255)
                    break;

                String playerName = report.getPlayerName();
                GamePlayer reportedPlayer = this.server.getPlayers().get(playerName);
                SanctionEntry banSanctionEntry = this.server.getDatabase().loadLastSanction(playerName, SanctionEntry.SanctionType.SANCTION_BAN);
                SanctionEntry muteSanctionEntry = this.server.getDatabase().loadLastSanction(playerName, SanctionEntry.SanctionType.SANCTION_MUTE);
                if (banSanctionEntry != null) {
                    if (banSanctionEntry.isPermanent) {
                        continue;
                    }

                    bannedPlayers.put(playerName, banSanctionEntry);
                } else if (reportedPlayer == null) {
                    disconnectedPlayers.add(playerName);
                }

                if (report.isResolved) {
                    deletedReports.add(new AbstractMap.SimpleEntry<>(playerName, report.resolvedBy));
                }

                String roomName = (reportedPlayer != null && reportedPlayer.getPlayerRoom() != null) ? reportedPlayer.getPlayerRoom().getRoomName() : "";
                int playedHours = (reportedPlayer != null) ? (int) (reportedPlayer.getPlayerData().playedTime / 3600) : (int) (report.playedTime / 3600);
                gameReports.add(new GameReport(cnt, playerName, report.playerCommunity.toUpperCase(), roomName, this.getRoomModerators(roomName), playedHours, report.getReporters(), muteSanctionEntry));
                cnt++;
            }

            NetByteArray packet = new NetByteArray();
            packet.writeByte(gameReports.size());
            for (GameReport report : gameReports) {
                packet.writeByte(report.reporters().getFirst().getReportType().getId());
                packet.writeUnsignedShort(report.reportId());
                packet.writeUTF(report.playerCommunity(), true);
                packet.writeUTF(report.playerName(), true);
                packet.writeUTF(report.roomName(), true);
                packet.writeByte(report.roomMods().size());
                for (String modName : report.roomMods()) {
                    packet.writeUTF(modName, true);
                }

                packet.writeInt(report.playedTime());
                packet.writeByte(report.reporters().size());
                for (ReportEntry.ReporterEntry reporter : report.reporters()) {
                    packet.writeUTF(reporter.getPlayerName(), true);
                    packet.writeShort((short) reporter.playerKarma);
                    packet.writeUTF(reporter.getReportComment(), true);
                    packet.writeByte(reporter.getReportType().getId());
                    packet.writeShort((short) ((Utils.getUnixTime() - reporter.getReportAge()) / 60));
                }

                SanctionEntry muteSanction = report.muteSanction();
                packet.writeBoolean(muteSanction != null);
                if (muteSanction != null) {
                    packet.writeUTF(muteSanction.getAuthor(), true);
                    packet.writeShort((short) (muteSanction.isPermanent == true ? 9999 : ((muteSanction.getExpirationDate() - Utils.getUnixTime()) / 3600) + 1));
                    packet.writeUTF(muteSanction.getReason(), true);
                }
            }

            moderatorPlayer.broadcastPacket(new PSSendPacket(PacketOpcodes.SEND_OPEN_MODOPWET) {
                @Override
                public PSByteArray build() {
                    return packet;
                }
            });

            for (SanctionEntry sanction : bannedPlayers.values()) {
                int remainingHours = (int) ((sanction.getExpirationDate() - Utils.getUnixTime()) / 3600);
                moderatorPlayer.broadcastPacket(new PacketSendModopwetPlayerBanned(sanction.getPlayerName(), sanction.getAuthor(), remainingHours, sanction.getReason()));
            }

            for (Map.Entry<String, String> deletedReport : deletedReports)
                moderatorPlayer.broadcastPacket(new PacketSendModopwetReportStatusDeleted(deletedReport.getKey(), deletedReport.getValue()));

            for (String playerName : disconnectedPlayers)
                moderatorPlayer.broadcastPacket(new PacketSendModopwetPlayerDisconnected(playerName));

            moderatorPlayer.broadcastPacket(new PacketSendModopwetCommunitiesList(Arrays.stream(LanguageInfo.values()).map(language -> language.getCode().toUpperCase()).toList()));
            Map<String, Integer> reportCounts = new TreeMap<>();
            for (ReportEntry report : this.reports) {
                if (report.isResolved || report.playerCommunity == null)
                    continue;

                reportCounts.merge(report.playerCommunity.toUpperCase(), 1, Integer::sum);
            }

            moderatorPlayer.broadcastPacket(new PacketSendModopwetCommunitiesReportsCount(reportCounts));
        }
    }

    /**
     * Handles when the player changes the room.
     * @param player The player's instance.
     */
    public void handlePlayerChangeRoom(GamePlayer player) {
        ReportEntry report = this.latestReportByPlayer.get(player.getPlayerName());
        if (report == null || report.isResolved)
            return;

        for (GamePlayer modPlayer : this.server.getPlayers().values()) {
            if (modPlayer.hasStaffPermission(StaffRoleType.TRIAL_MODO, false) || modPlayer.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) || modPlayer.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false))
                modPlayer.broadcastPacket(new PacketSendModopwetRoomPasswordProtected(player.getPlayerName(), player.getRoomName(), !player.getPlayerRoom().roomPassword.isEmpty()));
        }

        for (GamePlayer watcher : player.getModopwetWatchers()) {
            watcher.sendEnterRoom(player.getRoomName(), player.getPlayerRoom().roomPassword);
            watcher.broadcastPacket(new PacketSendServerMessage(true, String.format("<ROSE>[Modopwet]</ROSE> The player <BV>%s</BV> left the room <N>[%s]</N> and went to the room <N>[%s]</N>. %s", player.getPlayerName(), player.getLastRoomName(), player.getRoomName(), "(<CEP><a href='event:stopfollow'>Stop following</a></CEP>)")));
        }

        this.sendModopwetMessage(String.format("<ROSE>[Modopwet]</ROSE> The player <BV>%s</BV> left the room <N>[%s]</N> and went to the room <N>[%s]</N>. %s", player.getPlayerName(), player.getLastRoomName(), player.getRoomName(), String.format(" (<CEP><a href='event:join;%s'>Watch</a></CEP> - <CEP><a href='event:follow;%s'>Follow</a></CEP>)", player.getPlayerName(), player.getPlayerName())), player.getPlayerCommunity(), player.getModopwetWatchers());
    }

    /**
     * Handles when the reported player connects.
     * @param player The player's instance.
     */
    public void handlePlayerConnect(GamePlayer player) {
        ReportEntry report = this.latestReportByPlayer.get(player.getPlayerName());
        if (report == null || report.isResolved)
            return;

        report.playerConnectionStatus = ReportEntry.PlayerConnectionStatus.CONNECTED;
        report.playerCommunity = player.getPlayerCommunity().toUpperCase();
        this.sendModopwetMessage(String.format("<ROSE>[Modopwet]</ROSE> The player <BV>%s</BV> has connected in the room: <N>[%s]</N>. %s", player.getPlayerName(), player.getRoomName(), String.format(" (<CEP><a href='event:join;%s'>Watch</a></CEP> - <CEP><a href='event:follow;%s'>Follow</a></CEP>)", player.getPlayerName(), player.getPlayerName())), player.getPlayerCommunity(), Set.of());
    }

    /**
     * Handles when the reported player disconnects.
     * @param player The player's instance.
     */
    public void handlePlayerDisconnect(GamePlayer player) {
        ReportEntry report = this.latestReportByPlayer.get(player.getPlayerName());
        if (report == null || report.isResolved)
            return;

        for (GamePlayer watcher : player.getModopwetWatchers()) {
            if (watcher != null) {
                watcher.lastWatchedPlayer = null;
                watcher.isHidden = false;
                watcher.sendEnterRoom(this.server.getRecommendedRoom("", watcher.getPlayerCommunity()), "");
                watcher.broadcastPacket(new PacketSendServerMessage(true, String.format("<ROSE>[Modopwet]</ROSE> The player <N>%s</N> <N2>(%s)</N2> has disconnected from the game.", player.getPlayerName(), player.getRoomName())));
            }
        }

        player.getModopwetWatchers().clear();
        if (player.getIsGuest()) {
            this.reports.remove(report);
            List<ReportEntry> playerReports = this.reportsByPlayer.get(player.getPlayerName());
            if (playerReports != null) {
                playerReports.remove(report);
                if (playerReports.isEmpty()) {
                    this.reportsByPlayer.remove(player.getPlayerName());
                    this.latestReportByPlayer.remove(player.getPlayerName());
                } else {
                    ReportEntry latestReport = playerReports.getFirst();
                    for (ReportEntry entry : playerReports) {
                        if (entry.getReportCreationDate() > latestReport.getReportCreationDate())
                            latestReport = entry;
                    }

                    this.latestReportByPlayer.put(player.getPlayerName(), latestReport);
                }
            }

            return;
        }

        report.playerConnectionStatus = ReportEntry.PlayerConnectionStatus.DISCONNECTED;
        report.playedTime = player.getPlayerData().playedTime;
        this.sendModopwetMessage(String.format("<ROSE>[Modopwet]</ROSE> The player <N>%s</N> <N2>(%s)</N2> has disconnected from the game.", player.getPlayerName(), player.getRoomName()), player.getPlayerCommunity(), player.getModopwetWatchers());
    }

    /**
     * Handles when the player spies on the reported player.
     * @param moderatorPlayer The moderator's player instance.
     * @param playerName The reported player's name.
     * @param isFollowing Is the operation [follow] or [watch]
     */
    public void handleWatchReport(GamePlayer moderatorPlayer, String playerName, boolean isFollowing) {
        GamePlayer reportedPlayer = this.server.getPlayers().get(playerName);
        if (reportedPlayer == null) {
            moderatorPlayer.broadcastPacket(new PacketSendServerMessage(true, "The supplied argument isn't a valid nickname."));
            return;
        }

        if (moderatorPlayer.getPlayerName().equals(reportedPlayer.getPlayerName()))
            return;

        if (!isFollowing) {
            GamePlayer prev = moderatorPlayer.lastWatchedPlayer;
            if (prev != null && prev != reportedPlayer)
                prev.getModopwetWatchers().remove(moderatorPlayer);

            moderatorPlayer.lastWatchedPlayer = reportedPlayer;
            reportedPlayer.getModopwetWatchers().add(moderatorPlayer);
            moderatorPlayer.isHidden = true;
            moderatorPlayer.broadcastPacket(new PacketSendPlayerWatch(reportedPlayer.getPlayerName()));
        }

        moderatorPlayer.sendEnterRoom(reportedPlayer.getRoomName(), reportedPlayer.getPlayerRoom().roomPassword);
    }

    /**
     * Handles when the player stop watching on the reported player.
     * @param moderatorPlayer The moderator's player instance.
     */
    public void handleStopWatchReport(GamePlayer moderatorPlayer) {
        if (moderatorPlayer.lastWatchedPlayer != null) {
            moderatorPlayer.lastWatchedPlayer.getModopwetWatchers().remove(moderatorPlayer);
            moderatorPlayer.lastWatchedPlayer = null;
            moderatorPlayer.isHidden = false;
            moderatorPlayer.broadcastPacket(new PacketSendPlayerWatch(moderatorPlayer.getPlayerName()));
            moderatorPlayer.sendEnterRoom(this.server.getRecommendedRoom("", moderatorPlayer.getPlayerCommunity()), "");
        }
    }

    /**
     * Gets the moderators in the given room.
     * @param roomName The room's name.
     * @return List of moderator's player names.
     */
    private List<String> getRoomModerators(String roomName) {
        List<String> modPlayers = new ArrayList<>();
        if (roomName.isEmpty())
            return modPlayers;

        for (GamePlayer player : this.server.getRooms().get(roomName).getPlayers().values())
            if (player.hasStaffPermission(StaffRoleType.TRIAL_MODO, false) || player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) || player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false))
                modPlayers.add(player.getPlayerName());

        return modPlayers;
    }

    /**
     * Sends a message to every moderator who is subscribed to chat notifications.
     * @param message The message to send.
     * @param playerCommunity The reported's community.
     * @param excludedMods The excluded moderators who will not receive the message.
     */
    private void sendModopwetMessage(String message, String playerCommunity, Set<GamePlayer> excludedMods) {
        for (GamePlayer player : this.server.getPlayers().values()) {
            if (player.isModopwetSubscribed && player.modopwetSubscribedCommunities.contains(playerCommunity) && !excludedMods.contains(player))
                player.broadcastPacket(new PacketSendServerMessage(true, message));
        }
    }

    /**
     * Creates a new game report.
     * @param reportId The report's id.
     * @param playerName The reported's player name.
     * @param playerCommunity The reported's player community.
     * @param roomName The reported's player room.
     * @param roomMods the reported's room moderators.
     * @param playedTime The reported's played time.
     * @param reporters The reported's reporters.
     * @param muteSanction The reported's mute sanction if he has.
     */
    public record GameReport(int reportId, String playerName, String playerCommunity, String roomName, List<String> roomMods, int playedTime, List<ReportEntry.ReporterEntry> reporters, SanctionEntry muteSanction) {}
}