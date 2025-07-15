package org.transformice.database;

// Imports
import static dev.morphia.query.experimental.filters.Filters.elemMatch;
import static dev.morphia.query.experimental.filters.Filters.eq;
import static org.transformice.utils.Utils.getUnixTime;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Sort;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.transformice.Application;
import org.transformice.database.collections.*;
import org.transformice.database.embeds.CafePost;
import org.transformice.libraries.Pair;

public final class DBUtils {
    /**
     * Searches for account instance by given name.
     * @param nickname The given nickname.
     * @return The account instance if exist or else null.
     */
    public static Account findAccountByNickname(String nickname) {
        return DBManager.getDataStore().find(Account.class).filter(eq("playerName", nickname)).first();
    }

    /**
     * Searches for account instance by given name and password.
     * @param nickname The given nickname.
     * @param password The given password.
     * @return The account instance if exist or else null.
     */
    public static Account findAccountByPassword(String nickname, String password) {
        return DBManager.getDataStore().find(Account.class).filter(eq("playerName", nickname), eq("password", password)).first();
    }

    /**
     * Searches for all accounts that have the given email address.
     * @param email The given email address.
     * @return The accounts having the same email address.
     */
    public static List<Account> findAccountsByEmail(String email, String password) {
        if(password.isEmpty()) {
            return DBManager.getDataStore().find(Account.class).filter(eq("emailAddress", email)).stream().toList();
        }

        return DBManager.getDataStore().find(Account.class).filter(eq("emailAddress", email), eq("password", password)).stream().toList();
    }

    /**
     * Fetches all reports.
     * @return A list of reports.
     */
    public static List<Report> findAllReports() {
        return DBManager.getDataStore().find(Report.class).stream().toList();
    }

    /**
     * Searches for beta invite instance by given key.
     * @param key The given key.
     * @return The beta invite instance if exist or else null.
     */
    public static BetaInvite findBetaInviteByCode(String key) {
        BetaInvite info = DBManager.getDataStore().find(BetaInvite.class).filter(eq("beta_key", key)).first();
        if(info != null) {
            if(info.getEndDate() < getUnixTime()) {
                info.delete();
                return null;
            }
            return info;
        }
        return null;
    }

    /**
     * Searches for cafe posts made by given player.
     * @param playerName The player's name.
     * @return List of cafe posts.
     */
    public static List<CafePost> findCafePostsByPlayerName(String playerName) {
        List<CafePost> matchingPosts = new ArrayList<>();

        List<CafeTopic> topics = DBManager.getDataStore()
                .find(CafeTopic.class)
                .filter(elemMatch("posts", eq("author", playerName)))
                .iterator()
                .toList();

        for (CafeTopic topic : topics) {
            for (CafePost post : topic.getPosts()) {
                if (playerName.equals(post.getAuthor())) {
                    matchingPosts.add(post);
                }
            }
        }

        return matchingPosts;
    }

    /**
     * Searches for cafe posts by topic id.
     * @param topicId The given id.
     * @return The cafe posts if exist or else null.
     */
    public static CafeTopic findCafeTopicById(Long topicId) {
        return DBManager.getDataStore().find(CafeTopic.class).filter(eq("_id", topicId)).first();
    }

    /**
     * Searches for cafe topic by post id.
     * @param postId The given id.
     * @return The cafe topic if exist or else null.
     */
    public static CafeTopic findCafeTopicByPostId(Long postId) {
        return DBManager.getDataStore().find(CafeTopic.class).filter(elemMatch("posts", eq("_id", postId))).first();
    }

    /**
     * Searches for all cafe topics by given community.
     * @param community The given player's community.
     * @return The cafe topics if they exist.
     */
    public static List<CafeTopic> findCafeTopicsByCommunity(String community) {
        return DBManager.getDataStore().find(CafeTopic.class).filter(eq("community", community)).stream().toList();
    }

    /**
     * Fetches all connection logs off given player.
     * @param playerName The given playerName or IP Address.
     * @param isUsingIPAddress Is using ip address instead of nickname.
     * @return Connection logs
     */
    public static List<Loginlog> findConnectionLogs(String playerName, boolean isUsingIPAddress) {
        if(isUsingIPAddress) {
            return DBManager.getDataStore().find(Loginlog.class).filter(eq("ipAddress", playerName)).stream().limit(200).toList();
        }
        return DBManager.getDataStore().find(Loginlog.class).filter(eq("playerName", playerName)).stream().limit(200).toList();
    }

    /**
     * Searches for last active sanction by given name.
     * @param playerName The given player name.
     * @param punishType The given sanction type.
     * @return A sanction object if exist or else null.
     */
    public static Sanction findLatestSanction(String playerName, String punishType) {
        return DBManager.getDataStore().find(Sanction.class).filter(eq("playerName", playerName), eq("type", punishType), eq("state", "Active")).iterator(new FindOptions().sort(Sort.ascending("createdDate"))).tryNext();
    }

    /**
     * Fetches all sanctions of given player.
     * @param playerName The player name.
     * @return A list of sanction object.
     */
    public static List<Sanction> findSanctionsByAccount(String playerName) {
        return DBManager.getDataStore().find(Sanction.class).filter(eq("playerName", playerName)).stream().toList();
    }

    /**
     * Searches for tribe by given name.
     * @param tribeName The tribe name.
     * @return A tribe object.
     */
    public static Tribe findTribeByName(String tribeName) {
        return DBManager.getDataStore().find(Tribe.class).filter(eq("tribeName", tribeName)).first();
    }

    /**
     * Searches for map by given map code.
     * @param mapCode The map code.
     * @return A map object.
     */
    public static MapEditor findMapByCode(Integer mapCode) {
        return DBManager.getDataStore().find(MapEditor.class).filter(eq("mapCode", mapCode)).first();
    }

    /**
     * Searches for every map who was made by the given player name.
     * @param playerName The player name/
     * @return A list of map objects.
     */
    public static List<MapEditor> findMapByCreator(String playerName) {
        return DBManager.getDataStore().find(MapEditor.class).filter(eq("mapAuthor", playerName)).iterator().toList();
    }

    /**
     * Searches for a random map by given map category.
     * @param mapCategory The map category.
     * @return A map object.
     */
    public static MapEditor findMapByCategory(Integer mapCategory) {
        List<MapEditor> maps = DBManager.getDataStore().find(MapEditor.class).filter(eq("mapCategory", mapCategory)).iterator().toList();
        if (maps.isEmpty()) {
            Application.getLogger().warn(Application.getTranslationManager().get("mapcatnotfound", mapCategory));
            return null;
        }

        return maps.get(ThreadLocalRandom.current().nextInt(maps.size()));
    }

    /**
     * Searches for every map in the given map category.
     * @param mapCategory The map category.
     * @return A list of map objects.
     */
    public static List<MapEditor> findMapsByCategory(Integer mapCategory) {
        return DBManager.getDataStore().find(MapEditor.class).filter(eq("mapCategory", mapCategory)).iterator().toList();
    }

    /**
     * Searches for the top 10 players in a given criteria.
     * @param criteria The given criteria.
     * @return List of top 10 players and their scores.
     */
    public static List<Pair<Account, Integer>> findBest10PlayersByCriteria(String criteria) {
        List<Pair<Account, Integer>> top10List = new ArrayList<>();
        String lowerCriteria = criteria.toLowerCase();

        boolean requiresManualSort = switch (lowerCriteria) {
            case "racing", "survivor", "defilante" -> true;
            default -> false;
        };

        List<Account> accounts;
        if (requiresManualSort) {
            accounts = DBManager.getDataStore().find(Account.class).iterator().toList();
            accounts.sort(Comparator.comparingInt((Account acc) -> {
                return switch (lowerCriteria) {
                    case "racing" -> acc.getRacingStats()[2];
                    case "survivor" -> acc.getSurvivorStats()[3];
                    case "defilante" -> acc.getDefilanteStats()[2];
                    default -> 0;
                };
            }).reversed());

            if (accounts.size() > 10) {
                accounts = accounts.subList(0, 10);
            }
        } else {
            String fieldName = switch (lowerCriteria) {
                case "cheesecount" -> "cheeseCount";
                case "firstcount" -> "firstCount";
                case "shamancheesecount" -> "normalSaves";
                case "bootcampcount" -> "bootcampCount";
                default -> throw new IllegalArgumentException("Unknown criteria: " + criteria);
            };

            accounts = DBManager.getDataStore().find(Account.class).iterator(new FindOptions().sort(Sort.descending(fieldName)).limit(10)).toList();
        }

        for (Account acc : accounts) {
            int score = switch (lowerCriteria) {
                case "cheesecount" -> acc.getCheeseCount();
                case "firstcount" -> acc.getFirstCount();
                case "shamancheesecount" -> acc.getNormalSaves();
                case "bootcampcount" -> acc.getBootcampCount();
                case "racing" -> acc.getRacingStats()[0];
                case "survivor" -> acc.getSurvivorStats()[0];
                case "defilante" -> acc.getDefilanteStats()[0];
                default -> throw new IllegalArgumentException("Unknown criteria: " + criteria);
            };
            top10List.add(new Pair<>(acc, score));
        }

        return top10List;
    }
}