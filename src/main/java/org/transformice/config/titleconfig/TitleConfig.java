package org.transformice.config.titleconfig;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class TitleConfig {
    @JsonProperty("firstTitleList")
    private Map<Integer, TitleData> firstTitleList;
    @JsonProperty("cheeseTitleList")
    private Map<Integer, TitleData> cheeseTitleList;
    @JsonProperty("shopTitleList")
    private Map<Integer, TitleData> shopTitleList;
    @JsonProperty("bootcampTitleList")
    private Map<Integer, TitleData> bootcampTitleList;
    @JsonProperty("shamanTitleList")
    private Map<Integer, TitleData> shamanTitleList;
    @JsonProperty("hardModeTitleList")
    private Map<Integer, TitleData> hardModeTitleList;
    @JsonProperty("divineModeTitleList")
    private Map<Integer, TitleData> divineModeTitleList;
    @JsonProperty("shamanTitleListNoSkills")
    private Map<Integer, TitleData> shamanTitleListNoSkills;
    @JsonProperty("hardModeTitleListNoSkills")
    private Map<Integer, TitleData> hardModeTitleListNoSkills;
    @JsonProperty("divineModeTitleListNoSkills")
    private Map<Integer, TitleData> divineModeTitleListNoSkills;

    /**
     * Gets the first title list.
     * @return The first title list.
     */
    public Map<Integer, TitleData> getFirstTitleList() {
        return this.firstTitleList;
    }

    /**
     * Gets the cheese title list.
     * @return The cheese title list.
     */
    public Map<Integer, TitleData> getCheeseTitleList() {
        return this.cheeseTitleList;
    }

    /**
     * Gets the shop title list.
     * @return The shop title list.
     */
    public Map<Integer, TitleData> getShopTitleList() {
        return this.shopTitleList;
    }

    /**
     * Gets the bootcamp title list.
     * @return The bootcamp title list.
     */
    public Map<Integer, TitleData> getBootcampTitleList() {
        return this.bootcampTitleList;
    }

    /**
     * Gets the shaman title list.
     * @return The shaman title list.
     */
    public Map<Integer, TitleData> getShamanTitleList() {
        return this.shamanTitleList;
    }

    /**
     * Gets the shaman hard mode title list.
     * @return The shaman hard mode title list.
     */
    public Map<Integer, TitleData> getHardModeTitleList() {
        return this.hardModeTitleList;
    }

    /**
     * Gets the divine mode title list.
     * @return The divine mode title list.
     */
    public Map<Integer, TitleData> getDivineModeTitleList() {
        return this.divineModeTitleList;
    }

    /**
     * Gets the shaman title list without skills.
     * @return The shaman title list without skills.
     */
    public Map<Integer, TitleData> getShamanTitleListNoSkills() {
        return this.shamanTitleListNoSkills;
    }

    /**
     * Gets the shaman hard title list without skills.
     * @return The shaman title list without skills.
     */
    public Map<Integer, TitleData> getHardModeTitleListNoSkills() {
        return this.hardModeTitleListNoSkills;
    }

    /**
     * Gets the shaman divine title list without skills.
     * @return The shaman title list without skills.
     */
    public Map<Integer, TitleData> getDivineModeTitleListNoSkills() {
        return this.divineModeTitleListNoSkills;
    }
}