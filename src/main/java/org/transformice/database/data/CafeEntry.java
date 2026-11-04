package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.List;
import org.transformice.utils.Utils;

@Entity("cafe")
public class CafeEntry {
    @Id private long id;
    private String title;
    private String author;
    private Long authorId;
    private String community;
    private List<CafePost> posts;
    private Long date;
    private List<Long> reporters;
    public int reportScore;

    /**
     * Creates a new topic in the cafe.
     */
    public CafeEntry() {
        // Required by Morphia.
        this.posts = new ArrayList<>();
        this.reporters = new ArrayList<>();
    }

    /**
     * Creates a new topic in the cafe.
     * @param topicId The topic's id.
     * @param title Topic title.
     * @param author Topic author.
     * @param authorId Topic author id.
     * @param community Topic author's community.
     */
    public CafeEntry(final int topicId, final String title, final String author, final Long authorId, final String community) {
        this();
        this.id = topicId;
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.community = community;
        this.date = Utils.getUnixTime();
        this.reportScore = 0;
    }

    /**
     * Gets the cafe topic's author name.
     * @return The cafe topic's author name.
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Gets the topic's author id.
     * @return The topic's author id.
     */
    public long getAuthorId() {
        return this.authorId;
    }

    /**
     * Gets the cafe topic's community.
     * @return The cafe topic's community.
     */
    public String getCommunity() {
        return this.community;
    }

    /**
     * Gets the topic's creation date.
     * @return The topic's creation date.
     */
    public long getDate() {
        return this.date;
    }

    /**
     * Gets the topic's id.
     * @return The topic's id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Gets the topic's posts.
     * @return The topic's posts.
     */
    public List<CafePost> getPosts() {
        return this.posts;
    }

    /**
     * Gets the topic's bad score.
     * @return The topic's bad score.
     */
    public int getReportScore() {
        return this.reportScore;
    }

    /**
     * Gets the topic's reporters.
     * @return The topic's reporters.
     */
    public List<Long> getReporters() {
        return this.reporters;
    }

    /**
     * Gets the topic's title.
     * @return The topic's title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Creates a new cafe post.
     */
    @Embedded
    public static class CafePost {
        @Id private long id;
        private String message;
        private String author;
        private Long authorId;
        private Long date;
        private List<Long> votes;
        public Integer points;
        public Integer state;
        public String moderator;

        /**
         * Creates a new cafe post.
         */
        protected CafePost() {
            // Required by Morphia.
            this.votes = new ArrayList<>();
        }

        /**
         * Creates a new cafe post.
         * @param postId The post id.
         * @param message The post message.
         * @param author The post author.
         * @param authorId The post author's avatar id.
         */
        public CafePost(final int postId, String message, String author, Long authorId) {
            this();
            this.id = postId;
            this.message = message;
            this.author = author;
            this.authorId = authorId;
            this.state = 0;
            this.moderator = "";
            this.date = Utils.getUnixTime();
            this.points = 0;
        }

        /**
         * Gets the post's author.
         * @return The post's author.
         */
        public String getAuthor() {
            return this.author;
        }

        /**
         * Gets the post's author id.
         * @return The post's author id.
         */
        public long getAuthorId() {
            return this.authorId;
        }

        /**
         * Gets the post's creation date.
         * @return The post's creation date.
         */
        public long getDate() {
            return this.date;
        }

        /**
         * Gets the post's id.
         * @return The post's id.
         */
        public long getId() {
            return this.id;
        }

        /**
         * Gets the post's content.
         * @return The post's content.
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * Gets the post's voters.
         * @return The post's voters.
         */
        public List<Long> getVotes() {
            return this.votes;
        }
    }
}