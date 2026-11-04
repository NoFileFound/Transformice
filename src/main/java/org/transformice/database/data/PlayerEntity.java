package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.transformice.data.objects.MissionType;
import org.transformice.data.objects.PlayerGender;
import org.transformice.data.objects.PlayerTitleInfo;
import org.transformice.data.objects.ShamanType;
import org.transformice.data.objects.SkillType;
import org.transformice.data.objects.StaffRoleType;
import org.transformice.utils.Utils;

@Entity("accounts")
public class PlayerEntity {
    @Id private int accountId;
    private String username;
    public String emailAddress;
    public String password;
    private long registrationDate;
    private List<String> authorizedIps;
    public PlayerTitleInfo currentTitle;
    private List<String> friendList;
    private List<String> ignoredList;
    public boolean isVerifiedEmailAddress;
    public long lastConnectedTime;
    public int mouseColor;
    public PlayerGender playerGender;
    public String playerLook;
    public long playedTime;
    public String soulmate;
    private List<StaffRoleType> staffRoles;
    public int savesNormalWithSkill;
    public int savesNormalWithoutSkill;
    public int savesHardWithSkill;
    public int savesHardWithoutSkill;
    public int savesDivineWithSkill;
    public int savesDivineWithoutSkill;
    private List<Integer> shamanBadges;
    public int currentShamanBadge;
    public int shamanColor;
    private Map<SkillType, Integer> shamanSkills;
    public boolean shamanSkillsEnabled;
    public int shamanLevel;
    public int shamanLevelXp;
    public int shamanNextLevelXp;
    public ShamanType shamanType;
    public String shamanLook;
    public int shopCheese;
    public int shopStrawberries;
    private final List<String> shopClothes;
    private List<Integer> shopEmojis;
    private List<Integer> shopAdventureBanners;
    private Set<Integer> shopFavoriteItems;
    private Map<Integer, String> shopItems;
    private Map<Integer, String> shopShamanItems;
    private List<PlayerTitleInfo> unlockedTitles;
    public String tribeName;
    public TribeEntry.TribeRank tribeRank;
    public int cafeReputation;
    public boolean hasPublicAuthorization;
    public Object[] totemInfo;
    public long[] petInfo;
    public int playerKarma;
    private List<Mission> playerMissions;
    private RacingStats racingStats;
    private SurvivorStats survivorStats;
    private DefilanteStats defilanteStats;
    public int cheeseCount;
    public int shamanCheeseCount;
    public int firstCount;
    public int bootcampCount;
    private final Map<Integer, InventoryItem> inventory;
    private final List<Integer> equippedConsumables;
    private final List<String> letters;
    private final Map<Integer, Adventure> playerAdventures;
    public int adventurePoints;
    public int currentAdventureBanner;
    private final Map<Integer, Integer> playerBadges;

    /**
     * Creates a new player entity.
     */
    public PlayerEntity() {
        // Required by Morphia.
        this.friendList = new ArrayList<>();
        this.ignoredList = new ArrayList<>();
        this.staffRoles = new ArrayList<>();
        this.shamanSkills = new HashMap<>();
        this.shopEmojis = new ArrayList<>();
        this.unlockedTitles = new ArrayList<>();
        this.authorizedIps = new ArrayList<>();
        this.shamanBadges = new ArrayList<>();
        this.tribeRank = new TribeEntry.TribeRank();
        this.totemInfo = new Object[]{0, ""};
        this.petInfo = new long[]{0, 0};
        this.playerMissions = new ArrayList<>();
        this.inventory = new HashMap<>();
        this.equippedConsumables = new ArrayList<>();
        this.letters = new ArrayList<>();
        this.playerAdventures = new HashMap<>();
        this.registrationDate = 0;
        this.playerBadges = new HashMap<>();
        this.shopAdventureBanners = new ArrayList<>();
        this.shopClothes = new ArrayList<>();
        this.shopFavoriteItems = new HashSet<>();
        this.shopItems = new HashMap<>();
        this.shopShamanItems = new HashMap<>();
    }

    /**
     * Creates a new player entity.
     * @param username The player's username.
     * @param password The player's password.
     * @param email The player's email address.
     */
    public PlayerEntity(final int accountId, final String username, final String password, final String email, final String ipAddress) {
        this();
        this.accountId = accountId;
        this.username = username;
        this.emailAddress = email;
        this.password = password;
        this.registrationDate = Utils.getUnixTime();
        this.authorizedIps.add(ipAddress);
        this.currentTitle = new PlayerTitleInfo((short) 0, (byte) 0);
        this.unlockedTitles.add(this.currentTitle);
        this.isVerifiedEmailAddress = false;
        this.mouseColor = 7886906;
        this.playerGender = PlayerGender.UNSPECIFIED;
        this.playerLook = "1;0,0,0,0,0,0,0,0,0,0,0,0";
        this.shamanLook = "0,0,0,0,0,0,0,0,0";
        this.soulmate = "";
        this.shamanColor = 9820630;
        this.shamanSkillsEnabled = true;
        this.shamanLevel = 1;
        this.shamanNextLevelXp = 32;
        this.shamanType = ShamanType.NORMAL;
        this.tribeName = "";
        this.hasPublicAuthorization = false;
        this.racingStats = new RacingStats();
        this.survivorStats = new SurvivorStats();
        this.defilanteStats = new DefilanteStats();
        this.currentAdventureBanner = 1;
    }

    /**
     * Creates a new player entity.
     * @param username The player's username.
     */
    public PlayerEntity(String username, String ipAddress) {
        this(0, username, "", "", ipAddress);
    }

    /**
     * Gets the player's account id.
     * @return The player's account id.
     */
    public int getAccountId() {
        return this.accountId;
    }

    /**
     * Gets the player's authorized ip addresses.
     * @return List of player's authorized ip addresses.
     */
    public List<String> getAuthorizedIps() {
        return this.authorizedIps;
    }

    /**
     * Gets the player's friend list.
     * @return The player's friend list.
     */
    public List<String> getFriendList() {
        return this.friendList;
    }

    /**
     * Gets the player's ignored list.
     * @return The player's blocked list.
     */
    public List<String> getIgnoredList() {
        return this.ignoredList;
    }

    /**
     * Gets the player's letters.
     * @return The player's letters.
     */
    public List<String> getLetters() {
        return this.letters;
    }

    /**
     * Gets the player's adventures.
     * @return The player's adventures.
     */
    public Map<Integer, Adventure> getPlayerAdventures() {
        return this.playerAdventures;
    }

    /**
     * Gets the player's badges.
     * @return The player's badges.
     */
    public Map<Integer, Integer> getPlayerBadges() {
        return this.playerBadges;
    }

    /**
     * Gets the player's missions.
     * @return The player's missions.
     */
    public List<Mission> getPlayerMissions() {
        return this.playerMissions;
    }

    /**
     * Gets the date when the account was made.
     * @return The date when the account was made.
     */
    public long getRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * Gets the player's shaman badges.
     * @return The player's shaman badges.
     */
    public List<Integer> getShamanBadges() {
        return this.shamanBadges;
    }

    /**
     * Gets the player's shaman skills.
     * @return The player's shaman skills.
     */
    public Map<SkillType, Integer> getShamanSkills() {
        return this.shamanSkills;
    }

    /**
     * Gets the shaman type.
     * @return The shaman type.
     */
    public ShamanType getShamanType() {
        return this.shamanType;
    }

    /**
     * Gets the shop adventure banners.
     * @return The shop adventure banners.
     */
    public List<Integer> getShopAdventureBanners() {
        return this.shopAdventureBanners;
    }

    /**
     * Gets the shop's clothes.
     * @return The shop clothes.
     */
    public List<String> getShopClothes() {
        return this.shopClothes;
    }

    /**
     * Gets the shop emojis.
     * @return The shop emojis.
     */
    public List<Integer> getShopEmojis() {
        return this.shopEmojis;
    }

    /**
     * Gets the shop's favorite items.
     * @return The shop's favorite items.
     */
    public Set<Integer> getShopFavoriteItems() {
        return this.shopFavoriteItems;
    }

    /**
     * Gets the shop items.
     * @return The shop items.
     */
    public Map<Integer, String> getShopItems() {
        return this.shopItems;
    }

    /**
     * Gets the player's shop shaman items.
     * @return The player's shop shaman items.
     */
    public Map<Integer, String> getShopShamanItems() {
        return this.shopShamanItems;
    }

    /**
     * Gets the player's staff roles.
     * @return The player's staff roles.
     */
    public List<StaffRoleType> getStaffRoles() {
        return this.staffRoles;
    }

    /**
     * Gets the player's unlocked titles.
     * @return The player's unlocked titles.
     */
    public List<PlayerTitleInfo> getUnlockedTitles() {
        return this.unlockedTitles;
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the player's defilante stats.
     * @return The player's defilante stats.
     */
    public DefilanteStats getDefilanteStats() {
        return this.defilanteStats;
    }

    /**
     * Gets the player's racing stats.
     * @return The player's racing stats.
     */
    public RacingStats getRacingStats() {
        return this.racingStats;
    }

    /**
     * Gets the player's survivor stats.
     * @return The player's survivor stats.
     */
    public SurvivorStats getSurvivorStats() {
        return this.survivorStats;
    }

    /**
     * Gets the player's equipped items.
     * @return The player's equipped items.
     */
    public List<Integer> getEquippedConsumables() {
        return this.equippedConsumables;
    }

    /**
     * Gets the player's inventory.
     * @return The player's inventory.
     */
    public Map<Integer, InventoryItem> getInventory() {
        return this.inventory;
    }

    /**
     * Creates a new mission.
     */
    @Embedded
    public static class Mission {
        @Id private long id;
        private MissionType missionType;
        private int missionProgress;
        private int missionRequirement;
        private int missionAward;

        /**
         * Creates a new mission.
         * @param id The mission's id.
         * @param missionType The mission's type.
         * @param missionProgress The mission's progress.
         * @param missionRequirement The mission's requirement.
         * @param missionAward The mission's prize.
         */
        public Mission(int id, MissionType missionType, int missionProgress, int missionRequirement, int missionAward) {
            this.id = id;
            this.missionType = missionType;
            this.missionProgress = missionProgress;
            this.missionRequirement = missionRequirement;
            this.missionAward = missionAward;
        }
    }

    /**
     * Creates a survivor stats.
     */
    @Embedded
    public static class SurvivorStats {
        public int roundsCount;
        public int roundsShaman;
        public int roundsKilledMice;
        public int roundsSurvived;
    }

    /**
     * Creates a racing stats.
     */
    @Embedded
    public static class RacingStats {
        public int roundsCount;
        public int roundsCompleted;
        public int roundsCompletedFirstSecondThird;
        public int roundsCompletedFirst;
    }

    /**
     * Creates a defilante stats.
     */
    @Embedded
    public static class DefilanteStats {
        public int roundsCount;
        public int roundsCompletedFirstSecondThird;
        public int defilantePointsCount;
    }

    /**
     * Creates a new inventory item.
     */
    @Embedded
    public static class InventoryItem {
        @Id public int itemId;
        public int itemAmount;
        private String itemImage;

        /**
         * Creates a new item.
         * @param itemId The item's id.
         * @param itemAmount The item's amount.
         * @param itemImage The item's image.
         */
        public InventoryItem(int itemId, int itemAmount, String itemImage) {
            this.itemId = itemId;
            this.itemAmount = itemAmount;
            this.itemImage = itemImage;
        }

        /**
         * Gets the item's image.
         * @return The item's image.
         */
        public String getItemImage() {
            return this.itemImage;
        }
    }

    /**
     * Creates a new adventure.
     */
    @Embedded
    public static class Adventure {

    }
}