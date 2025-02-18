package org.transformice.database;

// Imports
import static dev.morphia.query.experimental.filters.Filters.eq;
import static org.transformice.utils.Utils.getUnixTime;
import java.util.List;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Sort;
import org.transformice.database.collections.*;

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
     * Searches for latest connection info of given player.
     * @param playerName The given player name.
     * @return A loginlog object if exist or else null.
     */
    public static Loginlog findLatestLogInfo(String playerName) {
        return DBManager.getDataStore().find(Loginlog.class).filter(eq("playerName", playerName)).iterator().tryNext();
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
}