package org.transformice;

// Imports
import java.io.IOException;
import java.util.ArrayList;
import org.fusesource.jansi.AnsiConsole;
import org.psframework.admin.PSAdminConfig;
import org.psframework.database.PSDatabaseConfig;
import org.psframework.database.PSDatabaseProvider;
import org.psframework.logging.PSLogger;
import org.psframework.utils.PSJson;
import org.transformice.config.TransformiceConfig;

public class Application {
    private static final PSLogger applicationLogger = new PSLogger();
    private static TransformiceConfig applicationConfig;

    /**
     * Entry point of the application.
     * @param args the command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        try {
            AnsiConsole.systemInstall();
            applicationConfig = PSJson.readFile("./config/properties.json", TransformiceConfig.class);
            applicationLogger.info("Loaded the server config.");
            new GameServer(new ArrayList<>(applicationConfig.getSWFConfig().getPorts()), new PSDatabaseConfig(PSDatabaseProvider.MONGODB, applicationConfig.getDBConfig().getHost(), applicationConfig.getDBConfig().getPort() == -1 ? 27017 : applicationConfig.getDBConfig().getPort(), applicationConfig.getDBConfig().getName(), applicationConfig.getDBConfig().getUsername(), applicationConfig.getDBConfig().getPassword()), new PSAdminConfig(applicationConfig.getAdmPanelConfig().getPort(), applicationConfig.getAdmPanelConfig().getUsername(), applicationConfig.getAdmPanelConfig().getPassword()), applicationLogger, applicationConfig).start();
        } catch (IOException ex) {
            applicationLogger.trace("Unable to load the server config.", ex);
        }

        AnsiConsole.systemUninstall();
    }
}