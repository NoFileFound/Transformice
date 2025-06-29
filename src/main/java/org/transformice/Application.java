package org.transformice;

// Imports
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.transformice.database.DBManager;
import org.transformice.libraries.GeoIP;
import org.transformice.libraries.JakartaMail;
import org.transformice.libraries.TranslationManager;
import org.transformice.properties.ConfigLoader;
import org.transformice.properties.configs.*;
import org.transformice.properties.configs.shop.*;

public final class Application {
    @Getter private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @Getter private static final TranslationManager translationManager = new TranslationManager();
    @Getter private static final Reflections reflector = new Reflections("org.transformice");
    @Getter private static SwfConfig.SWFClass swfInfo;
    @Getter private static PropertiesConfig.PropertiesClass propertiesInfo;
    @Getter private static Map<String, String[]> languageInfo;
    @Getter private static Map<String, Map<String, String>> captchaInfo;
    @Getter private static List<Map<String, String>> partnersInfo;
    @Getter private static List<String> badWordsConfig;
    @Getter private static Map<Integer, InventoryConfig.ConsumableInfo> inventoryInfo;
    @Getter private static Map<Integer, PromotionsConfig.Promotion> promotionsInfo;
    @Getter private static Map<Integer, ShopOutfitsConfig.ShopOutfit> shopOutfitsInfo;
    @Getter private static List<VillageNpcsConfig.VillageNPC> villageNPCSInfo;
    @Getter private static Map<Integer, ShopEmojisConfig.ShopEmoji> shopEmojiInfo;
    @Getter private static Map<Integer, ShopShamanItemConfig.ShopShamanItem> shopShamanItemInfo;
    @Getter private static Map<String, ShopItemConfig.ShopItem> shopItemInfo;
    @Getter private static Map<String, JapanExpoConfig.Code> japanExpoInfo;

    @SuppressWarnings("unchecked")
    private static void loadConfigVariables() {
        swfInfo = (SwfConfig.SWFClass) ConfigLoader.getProperty(SwfConfig.class).getInstance();
        propertiesInfo = (PropertiesConfig.PropertiesClass) ConfigLoader.getProperty(PropertiesConfig.class).getInstance();
        languageInfo = (Map<String, String[]>) ConfigLoader.getProperty(LanguageConfig.class).getInstance();
        captchaInfo = (Map<String, Map<String, String>>) ConfigLoader.getProperty(CaptchaConfig.class).getInstance();
        partnersInfo = (List<Map<String, String>>) ConfigLoader.getProperty(PartnersConfig.class).getInstance();
        badWordsConfig = (List<String>) ConfigLoader.getProperty(BadStringsConfig.class).getInstance();
        inventoryInfo = (Map<Integer, InventoryConfig.ConsumableInfo>) ConfigLoader.getProperty(InventoryConfig.class).getInstance();
        promotionsInfo = (Map<Integer, PromotionsConfig.Promotion>) ConfigLoader.getProperty(PromotionsConfig.class).getInstance();
        shopOutfitsInfo = (Map<Integer, ShopOutfitsConfig.ShopOutfit>) ConfigLoader.getProperty(ShopOutfitsConfig.class).getInstance();
        villageNPCSInfo = (List<VillageNpcsConfig.VillageNPC>) ConfigLoader.getProperty(VillageNpcsConfig.class).getInstance();
        shopEmojiInfo = (Map<Integer, ShopEmojisConfig.ShopEmoji>) ConfigLoader.getProperty(ShopEmojisConfig.class).getInstance();
        shopShamanItemInfo = (Map<Integer, ShopShamanItemConfig.ShopShamanItem>) ConfigLoader.getProperty(ShopShamanItemConfig.class).getInstance();
        shopItemInfo = (Map<String, ShopItemConfig.ShopItem>) ConfigLoader.getProperty(ShopItemConfig.class).getInstance();
        japanExpoInfo = (Map<String, JapanExpoConfig.Code>) ConfigLoader.getProperty(JapanExpoConfig.class).getInstance();
    }

    public static void main(String[] args) {
        ConfigLoader.loadConfig();
        loadConfigVariables();
        GeoIP.loadGeoDatabase();
        JakartaMail.initSmtpConfig();
        DBManager.initializeDatabase();
        Server server = new Server();
        server.startServer();
    }
}