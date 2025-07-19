package org.transformice.modules;

// Imports
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.transformice.Client;
import org.transformice.Server;
import org.transformice.database.DBUtils;
import org.transformice.database.collections.Account;
import org.transformice.database.collections.CafeTopic;
import org.transformice.database.embeds.CafePost;
import org.transformice.utils.Utils;

// Packets
import org.transformice.packets.send.cafe.*;
import org.transformice.packets.send.informations.C_LogMessage;
import org.transformice.packets.send.informations.C_TranslationMessage;
import org.transformice.packets.send.newpackets.C_ShowCafeWarnings;

public final class ParseCafe {
    private final Client client;
    private final Server server;
    private boolean canModerateCafe;
    private boolean canUseCafe;

    /**
     * Creates a new instance of Cafe for the given player.
     * @param client The given player.
     */
    public ParseCafe(final Client client) {
        this.client = client;
        this.server = client.getServer();
        this.canUseCafe = false;
        this.canModerateCafe = false;
    }

    /**
     * Function to init the cafe.
     */
    public void initCafeProperties() {
        this.canUseCafe = !this.client.isGuest() && this.client.getAccount().getCafeBadReputation() < 20;
        this.canModerateCafe = this.client.hasStaffPermission("Modo", "ModerateCafe") || this.client.hasStaffPermission("TrialModo", "ModerateCafe");
    }

    /**
     * Function to reload all cafe topics in client's community.
     */
    public void reloadCafeTopics() {
        List<CafeTopic> topics = DBUtils.findCafeTopicsByCommunity(this.client.playerCommunity);
        Map<Long, CafeTopic> cachedTopics = new HashMap<>();

        for(CafeTopic topic : topics) {
            topic.getPosts().removeIf(post -> post.getState() > 0 && !this.canModerateCafe);
            if(!topic.getPosts().isEmpty()) {
                cachedTopics.put(topic.getId(), topic);
            }
        }

        this.client.sendPacket(new C_CafeTopicList(cachedTopics, this.canUseCafe, this.canModerateCafe));
    }

    /**
     * Function to delete all comments posted by given player.
     * @param topicId Cafe topic id.
     * @param playerName The given player name.
     */
    public void sendDeleteAllCafePlayerPosts(long topicId, String playerName) {
        if(!this.canModerateCafe) return;

        CafeTopic myTopic = DBUtils.findCafeTopicById(topicId);
        if(myTopic == null) {
            return;
        }

        myTopic.getPosts().removeIf(post -> post.getAuthor().equals(playerName));
        if(myTopic.getPosts().isEmpty()) {
            myTopic.delete();
        } else {
            myTopic.save();
        }

        this.reloadCafeTopics();
        if(!myTopic.getPosts().isEmpty()) {
            this.sendTopicPosts(topicId, myTopic.getPosts());
        }
    }

    /**
     * Function to delete a post.
     * @param postId The given post id.
     */
    public void sendDeleteCafePost(long postId) {
        if (!this.canModerateCafe) {
            return;
        }

        CafeTopic myTopic = DBUtils.findCafeTopicByPostId(postId);
        if(myTopic == null) {
            return;
        }

        for (CafePost post : myTopic.getPosts()) {
            if (post.getId() == postId) {
                myTopic.getPosts().remove(post);
                break;
            }
        }

        if(myTopic.getPosts().isEmpty()) {
            myTopic.delete();
        } else {
            myTopic.save();
            this.sendTopicPosts(myTopic.getId(), myTopic.getPosts());
        }

        for(Client client : this.server.getPlayers().values()) {
            if(client.isOpenCafe) {
                client.sendPacket(new C_DeletePost(myTopic.getId(), postId));
            }
        }
    }

    /**
     * Function to create a new topic.
     * @param topicTitle The given topic title.
     * @param message The given topic message.
     */
    public void sendNewTopic(String topicTitle, String message) {
        if(!this.canUseCafe) {
            return;
        }

        CafeTopic myTopic = new CafeTopic(Utils.formatText(topicTitle), this.client.getPlayerName(), this.client.getAccount().getAvatarId(), this.client.playerCommunity);
        myTopic.getPosts().add(new CafePost(Utils.formatText(message), this.client.getPlayerName(), this.client.getAccount().getAvatarId()));
        myTopic.save();

        this.sendOpenCafe();
        this.reloadCafeTopics();

        this.client.sendPacket(new C_ShowTopicPosts(myTopic.getId(), myTopic.getPosts(), this.client.getAccount().getAvatarId(), false, true, this.canModerateCafe));
    }

    /**
     * Function to create a new comment on given topic.
     * @param topicId The given topic id.
     * @param message The comment message.
     */
    public void sendNewTopicPost(long topicId, String message) {
        if(!this.canUseCafe) {
            return;
        }

        CafeTopic myTopic = DBUtils.findCafeTopicById(topicId);
        if(myTopic == null) {
            return;
        }

        myTopic.getPosts().add(new CafePost(Utils.formatText(message), this.client.getPlayerName(), this.client.getAccount().getAvatarId()));
        myTopic.save();

        this.sendTopicPosts(topicId, myTopic.getPosts());
        for(Client client : this.server.getPlayers().values()) {
            if(client.isOpenCafe) {
                client.sendPacket(new C_CreateNewPost(topicId, this.client.getPlayerName(), myTopic.getPosts().size()));
            }
        }
    }

    /**
     * Function to give access to the cafe.
     */
    public void sendOpenCafe() {
        if(this.client.isGuest()) {
            this.client.sendPacket(new C_TranslationMessage("", "<ROSE>$PasAutoriseParlerSurServeur"));
        }

        this.client.sendPacket(new C_OpenCafe(this.canUseCafe));
        this.reloadCafeTopics();
        if(!this.client.isGuest()) {
            this.client.sendPacket(new C_ShowCafeWarnings(this.client.getAccount().getCafeBadReputation()));
        }
    }

    /**
     * Function to show all cafe posts of a given player.
     * @param playerName The given player name.
     */
    public void sendShowPlayerPosts(String playerName) {
        if(!this.canModerateCafe) {
            return;
        }

        List<CafePost> playerPosts = DBUtils.findCafePostsByPlayerName(playerName);
        StringBuilder postStr = new StringBuilder();
        for(CafePost post : playerPosts) {
            postStr.append("Id: <J>").append(post.getId()).append("</J>");
            postStr.append("<br>Date: <BL>").append(DateTimeFormatter.ofPattern("yyyy/MM/d").format(Instant.ofEpochMilli(post.getDate() * 1000).atZone(ZoneId.systemDefault()).toLocalDate())).append("</BL>");
            postStr.append("<br>Message: <G>").append(post.getMessage()).append("</G>");
            if(post.getState() != 0) {
                postStr.append("<br>Moderated by <ROSE>").append(post.getModerator()).append("</ROSE>");
            }
            postStr.append("<br><br>");
        }

        this.client.sendPacket(new C_LogMessage(1, postStr.toString()));
    }

    /**
     * Function to send all topic posts.
     * @param topicId The given topic id.
     */
    public void sendTopicPosts(long topicId, List<CafePost> posts) {
        boolean isUnderModeration = false;
        if(posts == null) {
            CafeTopic myTopic = DBUtils.findCafeTopicById(topicId);
            if (myTopic == null) {
                return;
            }
            posts = myTopic.getPosts();
            isUnderModeration = myTopic.getReportScore() > 0;
        }

        this.client.sendPacket(new C_ShowTopicPosts(topicId, posts, (this.client.getAccount() == null) ? -1 : this.client.getAccount().getId(), isUnderModeration, this.canUseCafe, this.canModerateCafe));
    }

    /**
     * Function to handle the reported post.
     * @param topicId The given topic id.
     * @param moderatePost Should moderate the post or keep  it.
     */
    public void handleReportedPost(long topicId, boolean moderatePost) {
        if(!this.canModerateCafe) {
            return;
        }

        String postAuthor = "";
        CafeTopic myTopic = DBUtils.findCafeTopicById(topicId);
        if(myTopic == null) {
            return;
        }

        myTopic.setReportScore(0);
        myTopic.getReporters().clear();
        for(CafePost post : myTopic.getPosts()) {
            if(post.getState() == 1) {
                post.setModerator(this.client.getPlayerName());
                post.setState((moderatePost) ? 2 : 0);
                postAuthor = post.getAuthor();
                break;
            }
        }

        myTopic.save();
        if(!postAuthor.isEmpty() && moderatePost) {
            Account authorAcc = DBUtils.findAccountByNickname(postAuthor);
            authorAcc.setCafeBadReputation((short) (authorAcc.getCafeBadReputation() + 1));
        }

        this.sendTopicPosts(myTopic.getId(), myTopic.getPosts());
    }

    /**
     * Function to report a given post.
     * @param topicId The topic id.
     * @param postId The post id.
     */
    public void reportCafePost(long topicId, long postId) {
        if(!this.canUseCafe) {
            return;
        }

        CafeTopic myTopic = DBUtils.findCafeTopicById(topicId);
        if(myTopic == null) {
            return;
        }

        long userId = this.client.getAccount().getId();
        if(!myTopic.getReporters().contains(userId)) {
            myTopic.setReportScore(myTopic.getReportScore() + 1);
            myTopic.getReporters().add(userId);

            for(CafePost post : myTopic.getPosts()) {
                if(post.getId() == postId) {
                    post.setState(1);
                    break;
                }
            }

            myTopic.save();
            this.server.sendServerMessage(String.format("The player <G>%s</G> reported the topic: <J>%s</J> (%s)", this.client.getPlayerName(), myTopic.getTitle(), this.client.playerCommunity), false, null);
        }
    }

    /**
     * Function to vote in a cafe post.
     * @param topicId The cafe topic id.
     * @param postId The cafe post id.
     * @param isPositive +1 or -1
     */
    public void voteCafePost(long topicId, long postId, boolean isPositive) {
        if(!this.canUseCafe) {
            return;
        }

        CafeTopic myTopic = DBUtils.findCafeTopicById(topicId);
        if(myTopic == null) {
            return;
        }

        long userId = this.client.getAccount().getId();
        for(CafePost post : myTopic.getPosts()) {
            if(post.getId() == postId && !post.getVotes().contains(userId)) {
                post.setPoints((short)((isPositive) ? post.getPoints() + 1 : post.getPoints() - 1));
                post.getVotes().add(userId);
                myTopic.save();
                break;
            }
        }

        this.sendTopicPosts(topicId, myTopic.getPosts());
    }
}