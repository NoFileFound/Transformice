package org.transformice.modules;

// Imports
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.psframework.utils.PSTimer;
import org.transformice.GamePlayer;
import org.transformice.GameServer;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.database.data.CafeEntry;
import org.transformice.database.data.PlayerEntity;
import org.transformice.database.data.SanctionEntry;
import org.transformice.modules.impl.ModuleServer;

// Packets
import org.transformice.packets.send.cafe.*;
import org.transformice.packets.send.informations.PacketSendTranslatedMessage;

public final class ModuleCafe extends ModuleServer {
    private final Map<String, PSTimer> reloadCafeTimer;
    private final Map<String, PSTimer> createCafeTopicTimer;
    private final Map<String, PSTimer> createCafePostTimer;

    /**
     * Creates a cafe module.
     *
     * @param server The game server.
     */
    public ModuleCafe(GameServer server) {
        super(server);
        this.createCafeTopicTimer = new ConcurrentHashMap<>();
        this.createCafePostTimer = new ConcurrentHashMap<>();
        this.reloadCafeTimer = new ConcurrentHashMap<>();
    }

    /**
     * Gets the reload cafe timer.
     * @return The reload cafe timer.
     */
    public PSTimer getCreateCafeReloadTimer(String playerName) {
        return this.reloadCafeTimer.computeIfAbsent(playerName, _ -> new PSTimer(this.server.getScheduler(), 2, TimeUnit.MILLISECONDS));
    }

    /**
     * Gets the cafe's post timer.
     * @param playerName The player's name.
     * @return The player's cafe post timer.
     */
    public PSTimer getCreateCafePostTimer(String playerName) {
        return this.createCafePostTimer.computeIfAbsent(playerName, _ -> new PSTimer(this.server.getScheduler(), 30, TimeUnit.SECONDS));
    }

    /**
     * Gets the cafe's topic timer.
     * @param playerName The player's name.
     * @return The player's cafe topic timer.
     */
    public PSTimer getCreateCafeTopicTimer(String playerName) {
        return this.createCafeTopicTimer.computeIfAbsent(playerName, _ -> new PSTimer(this.server.getScheduler(), 10, TimeUnit.MINUTES));
    }

    /**
     * Checks if the player can use the cafe like create topics or posts.
     * @param player The given player.
     * @return True if the player can access the cafe.
     */
    public boolean checkPlayerCanAccessCafe(GamePlayer player) {
        if (player.getIsGuest())
            return false;

        SanctionEntry muteSanction = this.server.getDatabase().loadLastSanction(player.getPlayerName(), SanctionEntry.SanctionType.SANCTION_MUTE);
        if (muteSanction != null)
			return false;

        return true;
    }

    /**
     * Checks if the player can moderate the cafe like remove posts or handle reported posts.
     * @param player The given player.
     * @return True if the player can moderate the cafe.
     */
    public boolean checkPlayerCanModerateCafe(GamePlayer player) {
        return player.hasStaffPermission(StaffRoleType.TRIAL_MODO, false) || player.hasStaffPermission(StaffRoleType.PUBLIC_MODO, false) || player.hasStaffPermission(StaffRoleType.PRIVATE_MODO, false);
    }

    /**
     * Reloads the cafe topics in the player's community.
     * @param player The player's instance.
     */
    public void reloadCafeTopics(GamePlayer player) {
        List<CafeEntry> topics = this.server.getDatabase().loadCafeByCommunity(player.getPlayerCommunity());
        Map<Long, CafeEntry> cachedTopics = new HashMap<>();
        for (CafeEntry topic : topics) {
            topic.getPosts().removeIf(post -> post.state > 0 && !this.checkPlayerCanModerateCafe(player));
            if (!topic.getPosts().isEmpty())
                cachedTopics.put(topic.getId(), topic);
        }

        player.broadcastPacket(new PacketSendCafeTopicList(cachedTopics, this.checkPlayerCanAccessCafe(player), this.checkPlayerCanModerateCafe(player)));
    }

    /**
     * Deletes all comments made by a player name in the cafe's topic.
     * @param player The player's instance.
     * @param topicId Cafe topic id.
     * @param playerName The given player name.
     */
    public void sendDeleteAllCafePlayerPosts(GamePlayer player, long topicId, String playerName) {
        if (!this.checkPlayerCanModerateCafe(player))
            return;

        CafeEntry myTopic = this.server.getDatabase().loadCafeTopicById(topicId);
        if (myTopic == null)
            return;

        myTopic.getPosts().removeIf(post -> post.getAuthor().equals(playerName));
        if (myTopic.getPosts().isEmpty()) {
            this.server.getDatabase().deleteCafe(myTopic);
        } else {
            this.server.getDatabase().saveCafe(myTopic);
        }

        this.reloadCafeTopics(player);
        if (!myTopic.getPosts().isEmpty())
            this.sendTopicPosts(player, topicId, myTopic.getPosts());
    }

    /**
     * Deletes a post.
     * @param player The player's instance.
     * @param postId The given post id.
     */
    public void sendDeleteCafePost(GamePlayer player, long postId) {
        if (!this.checkPlayerCanModerateCafe(player))
            return;

        CafeEntry myTopic = this.server.getDatabase().loadCafeTopicByPostId(postId);
        if (myTopic == null)
            return;

        for (CafeEntry.CafePost post : myTopic.getPosts()) {
            if (post.getId() == postId) {
                myTopic.getPosts().remove(post);
                break;
            }
        }

        if (myTopic.getPosts().isEmpty()) {
            this.server.getDatabase().deleteCafe(myTopic);
        } else {
            this.server.getDatabase().saveCafe(myTopic);
            this.sendTopicPosts(player, myTopic.getId(), myTopic.getPosts());
        }

        for (GamePlayer players : this.server.getPlayers().values()) {
            if (players.isOpenCafe)
                players.broadcastPacket(new PacketSendCafeDeletePost(myTopic.getId(), postId));
        }
    }

    /**
     * Creates a new topic.
     * @param player The player's instance.
     * @param topicTitle The given topic title.
     * @param message The given topic message.
     */
    public void sendNewTopic(GamePlayer player, String topicTitle, String message) {
        if (!this.checkPlayerCanAccessCafe(player))
            return;

        topicTitle = this.server.formatText(topicTitle);
        message = this.server.formatText(message);
        CafeEntry myTopic = new CafeEntry(this.server.getNextDatabaseCounterId("lastCafeTopicId"), topicTitle, player.getPlayerName(), player.getGlobalId(), player.getPlayerCommunity());
        myTopic.getPosts().add(new CafeEntry.CafePost(this.server.getNextDatabaseCounterId("lastCafePostId"), message, player.getPlayerName(), player.getGlobalId()));
        this.server.getDatabase().saveCafe(myTopic);
        this.sendOpenCafe(player);
        this.reloadCafeTopics(player);
        player.broadcastPacket(new PacketSendCafeOpenTopic(myTopic.getId(), myTopic.getPosts(), player.getGlobalId(), false, true, this.checkPlayerCanModerateCafe(player)));
    }

    /**
     * Creates a new post in the topic.
     * @param player The player's instance.
     * @param topicId The given topic id.
     * @param message The comment message.
     */
    public void sendNewTopicPost(GamePlayer player, long topicId, String message) {
        if (!this.checkPlayerCanAccessCafe(player))
            return;

        CafeEntry myTopic = this.server.getDatabase().loadCafeTopicById(topicId);
        if (myTopic == null)
            return;

        message = this.server.formatText(message);
        myTopic.getPosts().add(new CafeEntry.CafePost(this.server.getNextDatabaseCounterId("lastCafePostId"), message, player.getPlayerName(), player.getGlobalId()));
        this.server.getDatabase().saveCafe(myTopic);
        this.sendTopicPosts(player, topicId, myTopic.getPosts());
        for (GamePlayer client : this.server.getPlayers().values()) {
            if (client.isOpenCafe) {
                client.broadcastPacket(new PacketSendCafeCreatePost(topicId, player.getPlayerName(), myTopic.getPosts().size()));
            }
        }
    }

    /**
     * Opens the cafe.
     * @param player The player's instance.
     */
    public void sendOpenCafe(GamePlayer player) {
        if (!this.checkPlayerCanAccessCafe(player))
            player.broadcastPacket(new PacketSendTranslatedMessage("", "<ROSE>$PasAutoriseParlerSurServeur"));

        player.broadcastPacket(new PacketSendOpenCafe(this.checkPlayerCanAccessCafe(player)));
        this.reloadCafeTopics(player);
        if (!player.getIsGuest())
            player.broadcastPacket(new PacketSendCafeBadReputationScore((short) player.getPlayerData().cafeReputation));
    }

    /**
     * Sends all topic posts.
     * @param player The player's instance.
     * @param topicId The given topic id.
     * @param posts The topic's posts.
     */
    public void sendTopicPosts(GamePlayer player, long topicId, List<CafeEntry.CafePost> posts) {
        boolean isUnderModeration = false;
        if (posts == null) {
            CafeEntry myTopic = this.server.getDatabase().loadCafeTopicById(topicId);
            if (myTopic == null)
                return;

            posts = myTopic.getPosts();
            isUnderModeration = myTopic.getReportScore() > 0;
        }

        player.broadcastPacket(new PacketSendCafeOpenTopic(topicId, posts, player.getGlobalId(), isUnderModeration, this.checkPlayerCanAccessCafe(player), this.checkPlayerCanModerateCafe(player)));
    }

    /**
     * Function to handle the reported post.
     * @param player The player's instance.
     * @param topicId The given topic id.
     * @param moderatePost Should moderate the post or keep  it.
     */
    public void handleReportedPost(GamePlayer player, long topicId, boolean moderatePost) {
        if (!this.checkPlayerCanModerateCafe(player))
            return;

        String postAuthor = "";
        CafeEntry myTopic = this.server.getDatabase().loadCafeTopicById(topicId);
        if (myTopic == null)
            return;

        myTopic.reportScore = 0;
        myTopic.getReporters().clear();
        for (CafeEntry.CafePost post : myTopic.getPosts()) {
            if (post.state == 1) {
                post.moderator = player.getPlayerName();
                post.state = (moderatePost) ? 2 : 0;
                postAuthor = post.getAuthor();
                break;
            }
        }

        this.server.getDatabase().saveCafe(myTopic);
        if (!postAuthor.isEmpty() && moderatePost) {
            PlayerEntity authorAcc = (this.server.getPlayers().get(postAuthor) != null ? this.server.getPlayers().get(postAuthor).getPlayerData() : this.server.getDatabase().loadPlayerByName(postAuthor));
            authorAcc.cafeReputation++;
        }

        this.sendTopicPosts(player, myTopic.getId(), myTopic.getPosts());
    }

    /**
     * Reports a post.
     * @param player The player's instance.
     * @param topicId The topic id.
     * @param postId The post id.
     */
    public void reportCafePost(GamePlayer player, long topicId, long postId) {
        if (!this.checkPlayerCanAccessCafe(player))
            return;

        CafeEntry myTopic = this.server.getDatabase().loadCafeTopicById(topicId);
        if (myTopic == null)
            return;

        long userId = player.getGlobalId();
        if (!myTopic.getReporters().contains(userId)) {
            myTopic.reportScore++;
            myTopic.getReporters().add(userId);
            for (CafeEntry.CafePost post : myTopic.getPosts()) {
                if (post.getId() == postId) {
                    post.state = 1;
                    break;
                }
            }

            this.server.getDatabase().saveCafe(myTopic);
        }
        ///  TODO: [UNKNOWN] Investigate in the original game.
    }

    /**
     * Function to vote in a cafe post.
     * @param topicId The cafe topic id.
     * @param postId The cafe post id.
     * @param isPositive +1 or -1
     */
    public void voteCafePost(GamePlayer player, long topicId, long postId, boolean isPositive) {
        if (!this.checkPlayerCanAccessCafe(player))
            return;

        CafeEntry myTopic = this.server.getDatabase().loadCafeTopicById(topicId);
        if (myTopic == null)
            return;

        long userId = player.getGlobalId();
        for (CafeEntry.CafePost post : myTopic.getPosts()) {
            if (post.getId() == postId && !post.getVotes().contains(userId)) {
                post.points += (isPositive ? 1 : -1);
                post.getVotes().add(userId);
                this.server.getDatabase().saveCafe(myTopic);
                break;
            }
        }

        this.sendTopicPosts(player, topicId, myTopic.getPosts());
    }

    /**
     * Shows all posts of the player.
     * @param player The player's instance.
     */
    public void showCafePosts(GamePlayer player, String playerName) {
        if (!this.checkPlayerCanModerateCafe(player))
            return;

        ///  TODO: [UNKNOWN] Investigate in the original game.
    }
}