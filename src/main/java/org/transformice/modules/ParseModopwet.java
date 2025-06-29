package org.transformice.modules;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.transformice.Application;
import org.transformice.database.collections.Account;
import org.transformice.database.collections.Report;
import org.transformice.database.collections.Sanction;
import org.transformice.database.embeds.ReportReporter;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.libraries.Pair;

// Packets
import org.transformice.packets.send.chat.C_ServerMessage;
import org.transformice.packets.send.chat.C_StaffChannelMessage;
import org.transformice.packets.send.legacy.login.C_BanMessage;
import org.transformice.packets.send.legacy.login.C_ReportAnswer;
import org.transformice.packets.send.modopwet.*;

public final class ParseModopwet {
    public String modoPwetLangue = "ALL";
    private final Client client;
    private final Server server;
    private long lastOpened;

    /**
     * Creates a new instance of Modopwet for the given player.
     * @param client The client.
     */
    public ParseModopwet(final Client client) {
        this.client = client;
        this.server = client.getServer();
        this.lastOpened = getUnixTime();
    }

    /**
     * Creates a new report to show in modopwet.
     * @param playerName The player to report.
     * @param type The report type.
     * @param reason The report reason.
     */
    public void createGameReport(String playerName, int type, String reason) {
        if(playerName.equals(this.client.getPlayerName())) return;
        short playerKarma = this.client.getAccount().getPlayerKarma();
        if(this.server.getGameReports().containsKey(playerName) && !this.server.getGameReports().get(playerName).checkReporter(this.client.getPlayerName())) {
            this.client.sendOldPacket(new C_ReportAnswer(playerName));
            this.server.getGameReports().get(playerName).getReporters().add(new ReportReporter(this.client.getPlayerName(), reason, type, playerKarma));
            return;
        }

        Client playerClient = this.server.getPlayers().get(playerName);
        if(playerClient != null) {
            this.client.sendOldPacket(new C_ReportAnswer(playerName));
            this.server.getGameReports().put(playerName, new Report(playerClient, this.client.getPlayerName(), type, reason, playerKarma));
            for(Client player : this.server.getPlayers().values()) {
                if(player.hasStaffPermission("Modo", "Modopwet")) {
                    if(player.isSubscribedModoNotifications && player.getModopwetChatNotificationCommunities().contains(playerClient.playerCommunity)) {
                        player.sendPacket(new C_ServerMessage(true, String.format("<ROSE>[Modopwet] [%s]</ROSE> <BV>%s</BV> has been reported for %s in room [<N>%s</N>] %s", playerClient.playerCommunity, playerClient.getPlayerName(), this.getReportType(type), playerClient.getRoom().getRoomName(), playerClient.getRoom().getRoomName().equals(this.client.getRoom().getRoomName()) ? "" : String.format(" (<CEP><a href='event:join;%s'>Watch</a></CEP> - <CEP><a href='event:follow;%s'>Follow</a></CEP>)", playerClient.getPlayerName(), playerClient.getPlayerName()))));
                    } else if(player.getAccount().getModoCommunities().contains(playerClient.playerCommunity)) {
                        if(!player.getModoCommunitiesCount().containsKey(playerClient.playerCommunity)) {
                            player.getModoCommunitiesCount().put(playerClient.playerCommunity, 1);
                        } else {
                            player.getModoCommunitiesCount().put(playerClient.playerCommunity, player.getModoCommunitiesCount().get(playerClient.playerCommunity) + 1);
                        }
                    } else if(player.isOpenModopwet) {
                        player.getParseModopwetInstance().sendOpenModopwet(true);
                    }
                }
            }
        }
    }

    /**
     * Punishes the player for hacking permanently.
     * @param playerName The player name.
     * @param reason The reason.
     * @param isMuted Supposes the in-game [Moderation] message.
     */
    public void sendBanDef(String playerName, String reason, boolean isMuted) {
        Client playerClient = this.server.getPlayers().get(playerName);
        if(playerClient != null) {
            Sanction mySanction = this.server.getLatestSanction(playerName, "bandef");
            if(mySanction != null) {
                if(mySanction.getState().equals("Active")) {
                    this.client.sendPacket(new C_ServerMessage(true, Application.getTranslationManager().get("alreadybanned", playerName)));
                    return;
                }
                if(mySanction.getState().equals("Cancelled")) {
                    return;
                }
            }
            mySanction = new Sanction(playerName, playerClient.getIpAddress(), "bandef", this.client.getPlayerName(), reason, (long)-1);
            mySanction.save();

            this.server.sendServerMessage(Application.getTranslationManager().get("bandef_notify", this.client.getPlayerName(), playerName, reason), false, null);
            if(!isMuted) {
                playerClient.getRoom().sendAllOthers(playerClient, new C_StaffChannelMessage(0, "", "$MessageBanDefSalon", new ArrayList<>(List.of(playerName, reason))));
            }

            playerClient.sendOldPacket(new C_BanMessage(reason));
        }
    }

    /**
     * Punishes the player for hacking. (aka [ibanhack] or [banhack] options in the modopwet.)
     * @param playerName The player name.
     * @param isMuted Is [ibanhack] command.
     */
    public void sendBanHack(String playerName, boolean isMuted) {
        Client playerClient = this.server.getPlayers().get(playerName);
        if(playerClient != null) {
            Sanction mySanction = this.server.getLatestSanction(playerName, "banjeu");
            int banHours = 0;
            if(mySanction != null) {
                if(mySanction.getState().equals("Active")) {
                    this.client.sendPacket(new C_ServerMessage(true, Application.getTranslationManager().get("alreadybanned", playerName)));
                    return;
                }
                if(mySanction.getState().equals("Cancelled")) {
                    return;
                }
                banHours = (int)((mySanction.getExpirationDate() - mySanction.getCreatedDate()) / 3600);
            }
            banHours += 360;
            mySanction = new Sanction(playerName, playerClient.getIpAddress(), "banjeu", this.client.getPlayerName(), "$MessageTriche", getUnixTime() + (banHours * 3600L));
            mySanction.save();

            this.server.sendServerMessage(Application.getTranslationManager().get("banplayernotify", this.client.getPlayerName(), playerName, banHours, "$MessageTriche"), false, null);
            if(!isMuted) {
                playerClient.getRoom().sendAllOthers(playerClient, new C_StaffChannelMessage(0, "", "$Message_Ban", new ArrayList<>(List.of(playerName, String.valueOf(banHours), "$MessageTriche"))));
            }

            playerClient.sendOldPacket(new C_BanMessage(banHours, "$MessageTriche"));
        }
    }

    /**
     * Sends the #ChatLog (game chat & whispers) of given player.
     * @param playerName The given player name.
     */
    public void sendChatLog(String playerName) {
        var chatMessages = this.server.getChatMessages().get(playerName);
        var whisperMessages = this.server.getWhisperMessages().get(playerName);

        if(whisperMessages == null && chatMessages == null) {
            this.client.sendPacket(new C_ServerMessage(true, Application.getTranslationManager().get("chatlog_noresult", playerName)));
            return;
        }

        this.client.sendPacket(new C_ChatLog(playerName, chatMessages, whisperMessages));
    }

    /**
     * Deletes the report from modopwet by reported player.
     * @param playerName The reported player.
     * @param isHandled Is [handled] command.
     */
    public void sendDeleteReport(String playerName, boolean isHandled) {
        if(isHandled) {
            for (ReportReporter reporter : this.server.getGameReports().get(playerName).getReporters()) {
                if(!reporter.getPlayerName().startsWith("*")) {
                    Account reporterAccount = this.server.getPlayerAccount(reporter.getPlayerName());
                    reporterAccount.setPlayerKarma((short) (reporterAccount.getPlayerKarma() + 1));
                    reporterAccount.save();
                    if (this.server.checkIsConnected(reporter.getPlayerName())) {
                        this.server.getPlayers().get(reporter.getPlayerName()).sendPacket(new C_ServerMessage(true, String.format("$Traitement_Signalement (Karma %s)", reporterAccount.getPlayerKarma()), new ArrayList<>(List.of(playerName))));
                    }
                }
            }
        }

        this.server.getGameReports().get(playerName).setIsDeleted(true);
        this.server.getGameReports().get(playerName).setDeletedBy(this.client.getPlayerName());
        if(this.server.deleteModopwetReportTimer.getRemainingTime() <= 0) {
            this.server.getGameReports().get(playerName).delete();
            this.server.getGameReports().remove(playerName);
            this.server.deleteModopwetReportTimer.schedule(() -> {}, TimeUnit.DAYS);
        }
        this.sendOpenModopwet(this.client.isOpenModopwet);
    }

    /**
     * Opens the modopwet.
     * @param isOpen Is opening.
     */
    public void sendOpenModopwet(boolean isOpen) {
        this.sendOpenModopwet(isOpen, false, false, false);
    }

    /**
     * Opens the modopwet.
     * @param isOpen Is opening.
     * @param hasOnePersonReports Shows only the first report.
     * @param isSortedByTime Are reports sorted by time.
     * @param isForwardSorted Are reports forward sorted.
     */
    public void sendOpenModopwet(boolean isOpen, boolean hasOnePersonReports, boolean isSortedByTime, boolean isForwardSorted) {
        if(isOpen) {
            if(getUnixTime() - this.lastOpened < 1.5) return;
            this.lastOpened = getUnixTime();

            List<Object[]> gameReports = new ArrayList<>();
            List<String> disconnectedPlayers = new ArrayList<>();
            List<Sanction> bannedPlayers = new ArrayList<>();
            List<Pair<String, String>> deletedReports = new ArrayList<>();
            int cnt = 0;

            for(var report : this.sortReports(isSortedByTime, isForwardSorted).entrySet()) {
                Client playerClient = this.server.getPlayers().get(report.getKey());
                Sanction myMuteSanction = this.server.getLatestSanction(report.getKey(), "mutejeu");
                if(myMuteSanction == null) {
                    myMuteSanction = this.server.getLatestSanction(report.getKey(), "mutedef");
                }
                Sanction myBanSanction = this.server.getLatestSanction(report.getKey(), "banjeu");
                if(myBanSanction == null) {
                    myBanSanction = this.server.getLatestSanction(report.getKey(), "bandef");
                }

                if(myBanSanction != null) {
                    bannedPlayers.add(myBanSanction);
                }

                if(playerClient == null && myBanSanction == null) disconnectedPlayers.add(report.getKey());
                if(!report.getValue().getPlayerCommunity().equals(this.modoPwetLangue) && !this.modoPwetLangue.equals("ALL")) continue;

                if(hasOnePersonReports && playerClient != null && cnt > 1) continue;
                if(cnt > 255) continue;

                if(report.getValue().getIsDeleted()) {
                    deletedReports.add(new Pair<>(report.getKey(), report.getValue().getDeletedBy()));
                }

                gameReports.add(new Object[]{
                        cnt,
                        report.getKey(),
                        report.getValue().getPlayerCommunity(),
                        (playerClient != null) ? playerClient.getRoom().getRoomName() : "0",
                        (playerClient != null) ? this.getRoomModerators(playerClient.getRoom().getRoomName()) : new ArrayList<>(),
                        report.getValue().getPlayedHours(),
                        report.getValue().getReporters(),
                        myMuteSanction
                });
                cnt++;
            }

            this.client.sendPacket(new C_OpenModopwet(gameReports));

            // deleted reports
            for(var deletedReport : deletedReports) {
                this.client.sendPacket(new C_ModopwetDeletedMsg(deletedReport.getFirst(), deletedReport.getSecond()));
            }

            // banned people.
            for(Sanction mySanction : bannedPlayers) {
                this.client.sendPacket(new C_ModopwetBannedMsg(mySanction.getPlayerName(), mySanction.getAuthor(), (int)((mySanction.getExpirationDate() - getUnixTime()) / 3600) + 1, mySanction.getReason()));
            }

            // disconnected people.
            for(String player : disconnectedPlayers) {
                this.client.sendPacket(new C_ModopwetDisconnectedMsg(player));
            }

            // sends the modopwet communities.
            this.sendModoLanguages();

            // sends the modopwet communities new report count.
            this.client.getModoCommunitiesCount().remove(this.modoPwetLangue);
            this.client.sendPacket(new C_ModopwetCommunitiesCount(this.client.getModoCommunitiesCount()));
        }
    }

    /**
     * Turns all mice semi-transparent and their messages are darker except the given player.
     * @param playerName The player name.
     */
    public void sendWatchPlayer(String playerName) {
        this.client.sendPacket(new C_WatchPlayer(playerName));
    }

    /**
     * Watches the current reported player.
     * @param playerName The reported player.
     * @param isFollowing Is using the [follow] command.
     */
    public void sendWatchReport(String playerName, boolean isFollowing) {
        Client playerClient = this.server.getPlayers().get(playerName);
        if(playerClient != null && !playerName.equals(this.client.getPlayerName())) {
            if(isFollowing) {
                this.client.sendEnterRoom(playerClient.getRoom().getRoomName(), playerClient.getRoom().getRoomPassword());
            } else {
                if(this.client.lastWatchedClient != null) {
                    this.client.lastWatchedClient.getCurrentWatchers().remove(this.client);
                }

                if(!playerClient.getCurrentWatchers().contains(this.client)) {
                    playerClient.getCurrentWatchers().add(this.client);
                    this.client.isHidden = true;
                    this.sendWatchPlayer(playerName);
                    this.client.sendEnterRoom(playerClient.getRoom().getRoomName(), playerClient.getRoom().getRoomPassword());
                    this.client.lastWatchedClient = playerClient;
                }
            }
        } else if(playerClient == null) {
            this.client.sendPacket(new C_ServerMessage(true, Application.getTranslationManager().get("invalidusername")));
        }
    }

    /**
     * Gets the report type as a string.
     * @param reportType The report type.
     * @return The report type as a string (for example 0 is Hack).
     */
    private String getReportType(int reportType) {
        return switch (reportType) {
            case 0 -> "Hack";
            case 1 -> "Spam / Flood";
            case 2 -> "Insults";
            case 3 -> "Phishing";
            default -> "Other";
        };
    }

    /**
     * Gets all moderators in the given room name.
     * @param roomName The room name.
     * @return A list of moderators.
     */
    private List<String> getRoomModerators(String roomName) {
        List<String> roomMods = new ArrayList<>();
        for(Client player : this.server.getRooms().get(roomName).getPlayers().values()) {
            if(player.getAccount().getPrivLevel() >= 9) roomMods.add(player.getPlayerName());
        }
        return roomMods;
    }

    /**
     * Sends the communities in the modopwet.
     */
    private void sendModoLanguages() {
        this.client.sendPacket(new C_ModopwetAddLangues(this.client.getAccount().getModoCommunities()));
    }

    /**
     * Sorts the reports by time.
     * @param isSortedByTime Sort the reports by time.
     * @param isForwardSorted Forward sort the reports.
     * @return The sorted report If options are selected or don't change the map.
     */
    private Map<String, Report> sortReports(boolean isSortedByTime, boolean isForwardSorted) {
        List<Map.Entry<String, Report>> entryList = new ArrayList<>(this.server.getGameReports().entrySet());
        if(isSortedByTime) {
            entryList.sort((o1, o2) -> {
                Report v2 = o1.getValue();
                Report v1 = o2.getValue();
                return v1.getTimestamp() < v2.getTimestamp() ? -1 : 1;
            });
        }

        else if(isForwardSorted) {
            entryList.sort((o1, o2) -> {
                Report v2 = o1.getValue();
                Report v1 = o2.getValue();
                int res = Integer.compare(v1.getReporters().size(), v2.getReporters().size());
                if (res == 0) {
                    return v1.getPlayedHours() < v2.getPlayedHours() ? -1 : 1;
                } else {
                    return res;
                }
            });
        }

        Map<String, Report> sortedMap = new HashMap<>();
        for (Map.Entry<String, Report> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}