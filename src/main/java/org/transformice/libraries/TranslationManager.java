package org.transformice.libraries;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

public final class TranslationManager {
    private final Map<String, String> translations;

    /**
     * Initializes the translation manager by loading translations from file.
     */
    public TranslationManager() {
        Map<String, String> loadedTranslations = null;

        try (FileReader reader = new FileReader("./config/cnsl_translation.json")) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            String systemLang = Locale.getDefault().getLanguage().toUpperCase();
            JsonObject langObject = root.has(systemLang)
                    ? root.getAsJsonObject(systemLang)
                    : root.getAsJsonObject("EN");

            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            loadedTranslations = new Gson().fromJson(langObject, mapType);

        } catch (IOException e) {
            System.err.println("The translations file could not be found or accessed. The program will now close.");
            System.exit(1);
        }

        this.translations = loadedTranslations;
    }

    /**
     * Retrieves the translated text for the given key.
     *
     * @param key  The translation key.
     * @param args Optional arguments for formatting.
     * @return The formatted translated string or the key itself if not found.
     */
    public String get(String key, Object... args) {
        String template = translations.getOrDefault(key, key);
        try {
            return String.format(template, args);
        } catch (Exception e) {
            return template;
        }
    }
}