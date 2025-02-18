package org.transformice;

// Imports
import java.util.Map;
import lombok.Getter;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.transformice.database.DBManager;
import org.transformice.libraries.GeoIP;
import org.transformice.properties.ConfigLoader;
import org.transformice.properties.configs.CaptchaConfig;
import org.transformice.properties.configs.LanguageConfig;
import org.transformice.properties.configs.PropertiesConfig;
import org.transformice.properties.configs.SwfConfig;

public class Application {
    @Getter private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @Getter private static final Reflections reflector = new Reflections("org.transformice");
    @Getter private static SwfConfig.SWFClass swfInfo;
    @Getter private static PropertiesConfig.PropertiesClass propertiesInfo;
    @Getter private static Map<String, String[]> languageInfo;
    @Getter private static Map<String, Map<String, String>> captchaInfo;

    private static void loadConfigVariables() {
        swfInfo = (SwfConfig.SWFClass) ConfigLoader.getProperty(SwfConfig.class).getInstance();
        propertiesInfo = (PropertiesConfig.PropertiesClass) ConfigLoader.getProperty(PropertiesConfig.class).getInstance();
        languageInfo = (Map<String, String[]>) ConfigLoader.getProperty(LanguageConfig.class).getInstance();
        captchaInfo = (Map<String, Map<String, String>>) ConfigLoader.getProperty(CaptchaConfig.class).getInstance();
    }

    public static void main(String[] args) {
        ConfigLoader.loadConfig();
        loadConfigVariables();
        GeoIP.loadGeoDatabase();
        DBManager.initializeDatabase();
        Server server = new Server();
        server.startServer();
    }
}