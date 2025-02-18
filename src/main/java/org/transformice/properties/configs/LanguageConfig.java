package org.transformice.properties.configs;

// Imports
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;
import org.transformice.Application;
import org.transformice.libraries.JsonLoader;
import org.transformice.properties.Property;

public final class LanguageConfig implements Property {
    private Map<String, String[]> languageInstance;

    @Override
    public Object getInstance() {
        return this.languageInstance;
    }

    @Override
    public void loadFile() {
        this.languageInstance = JsonLoader.loadJson("server/language.json", new TypeToken<Map<String, String[]>>() {}.getType());
        if(this.languageInstance == null) {
            Application.getLogger().warn("Unable to load the languages config, switching to default languages.");
            this.languageInstance = new HashMap<>();
            this.languageInstance.put("en", new String[]{"English", "gb", "false", "false", ""});
            this.languageInstance.put("br", new String[]{"Português brasileiro", "br", "false", "true", ""});
            this.languageInstance.put("pt", new String[]{"Português", "pt", "false", "true", ""});
            this.languageInstance.put("ro", new String[]{"Română", "ro", "false", "true", ""});
            this.languageInstance.put("ja", new String[]{"日本語", "jp", "false", "true", ""});
        } else {
            Application.getLogger().info(String.format("Loaded total community languages %d", this.languageInstance.size()));
        }
    }

    @Override
    public void saveFile() {

    }
}